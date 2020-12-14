package client;

import io.swagger.client.ApiClient;
import io.swagger.client.api.SkiersApi;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Runner {
  public void run(SkiersApi skiersApi) {
    Config config = new Config();
    try {
      LoadPropertiesFile.load(config);
    } catch (IOException e) {
      e.printStackTrace();
    }
    final int MAX_THREADS = config.getMAX_THREADS();
    final int SKIER_NUMBER = config.getSKIER_NUMBER();
    final String IP = config.getIP();
    final String PORTAL = config.getPORTAL();
    final String BASE_DIR = config.getBASE_DIR();
    String basePath = String.format("http://%s:%s/%s", IP, PORTAL, BASE_DIR);
    ApiClient client = skiersApi.getApiClient();
    client.setBasePath(basePath);

    RequestCount requestCount = new RequestCount();
    int phaseOneNumThreads = MAX_THREADS / 4;
    int phaseTwoNumThreads = MAX_THREADS;
    int phaseThreeNumThreads = MAX_THREADS / 4;

    CountDownLatch countDownLatch = new CountDownLatch(phaseOneNumThreads + phaseTwoNumThreads + phaseThreeNumThreads);

    long startTime = System.currentTimeMillis();

    // phase one
    System.out.println("Phase one start!");
    int num = SKIER_NUMBER / phaseOneNumThreads;
    int startID = 1;
    CountDownLatch countDownLatchOne= new CountDownLatch((int)Math.ceil(phaseOneNumThreads * 0.1));

    for (int i = 0; i < phaseOneNumThreads; i++) {
      new Thread(new ClientThread(1000, 5, new int[]{startID, startID + num - 1}, new int[]{1, 90}, config, skiersApi, new CountDownLatch[]{countDownLatch, countDownLatchOne}, requestCount)).start();
      startID = startID + num;
    }

    try {
      countDownLatchOne.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // phase two
    System.out.println("Phase two start!");
    num = SKIER_NUMBER / phaseTwoNumThreads;
    startID = 1;
    CountDownLatch countDownLatchTwo = new CountDownLatch((int)Math.ceil(phaseTwoNumThreads * 0.1));

    for (int i = 0; i < phaseTwoNumThreads; i++) {
      new Thread(new ClientThread(1000, 5, new int[]{startID, startID + num - 1}, new int[]{91, 360}, config, skiersApi, new CountDownLatch[]{countDownLatch, countDownLatchTwo}, requestCount)).start();
      startID = startID + num;
    }

    try {
      countDownLatchTwo.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // phase three
    System.out.println("Phase three start!");
    num = SKIER_NUMBER / phaseThreeNumThreads;
    startID = 1;
    for (int i = 0; i < phaseThreeNumThreads; i++) {
      new Thread(new ClientThread(1000, 10, new int[]{startID, startID + num - 1}, new int[]{361, 420}, config, skiersApi, new CountDownLatch[]{countDownLatch}, requestCount)).start();
      startID = startID + num;
    }

    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();

    if (!(skiersApi instanceof LogSkiersApi)) {
      System.out.printf("Done! Success Request: %d, Failed Requst: %d, Total Time: %d ms, Throughput: %.1f requests per second \n", requestCount.getSuccessCount(), requestCount.getFailCount(), (endTime - startTime) , (requestCount.getSuccessCount() * 1.0 / (endTime - startTime) * 1.0) * 1000);
    } else {
      ((LogSkiersApi) skiersApi).done();
      try {
        Caculator caculator = new Caculator("record.csv");
        System.out.printf("Get Statistics Info:\n");
        System.out.printf("Mean Response Time: %d ms,  Median Response Time: %d ms, P99 response time %d ms, Max Response Time: %d ms\n", caculator.getGetsMeanResponseTime(), caculator.getGetsMedianResponseTime(), caculator.get99GetsResponseTime(), caculator.getMaxGetsResponseTime());
        System.out.printf("Post Statistics Info:\n");
        System.out.printf("Mean Response Time: %d ms,  Median Response Time: %d ms, P99 response time %d ms, Max Response Time: %d ms\n", caculator.getPostsMeanResponseTime(), caculator.getPostsMedianResponseTime(), caculator.get99PostsResponseTime(), caculator.getMaxPostsResponseTime());
        System.out.printf("General Statistics Info:\n");
        System.out.printf("Total Time: %d ms, Throughput: %.1f requests per second \n", (endTime - startTime), (requestCount.getSuccessCount() * 1.0 / (endTime - startTime) * 1.0) * 1000);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
