package client;

import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import io.swagger.client.model.SkierVertical;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientThread implements Runnable {
  private int numPost;
  private int numGet;
  private int[] skiersIdRange;
  private int[] timeRange;
  private Config config;
  private SkiersApi skiersApi;
  private CountDownLatch[] counts;
  private RequestCount request;

  public ClientThread(int numPost, int numGet, int[] skiersIdRange, int[] timeRange,
      Config config, SkiersApi skiersApi, CountDownLatch[] counts, RequestCount request) {
    this.numPost = numPost;
    this.numGet = numGet;
    this.skiersIdRange = skiersIdRange;
    this.timeRange = timeRange;
    this.config = config;
    this.skiersApi = skiersApi;
    this.counts = counts;
    this.request = request;
  }

  @Override
  public void run() {
    Logger logger = LogManager.getLogger(ClientThread.class);

    Random random = new Random();
    int startID = skiersIdRange[0];
    int endID = skiersIdRange[1];
    int startTime = timeRange[0];
    int endTime = timeRange[1];
    String dayID = String.valueOf(config.getSKI_DAY());
    String resortID = config.getRESORT_NAME();
    int liftNum = config.getLIFTS_NUMBER();
    // post
    for (int i = 0; i < numPost; i++) {
      try {
        LiftRide liftRide = new LiftRide();
        liftRide.setSkierID(String.valueOf(startID + random.nextInt(endID - startID + 1)));
        liftRide.setDayID(dayID);
        liftRide.setResortID(resortID);
        liftRide.setTime(String.valueOf(startTime + random.nextInt(endTime - startTime + 1)));
        liftRide.setLiftID(String.valueOf(random.nextInt(liftNum) + 1));
        skiersApi.writeNewLiftRideWithHttpInfo(liftRide);
        request.increaseSuccessCnt();
      } catch (ApiException e) {
        request.increaseFailCnt();
        logger.error("Write New Lift Ride Faild: " + e.getMessage());
      }
    }

    // get
    for (int i = 0; i < numGet; i++) {
      try {
        skiersApi.getSkierDayVerticalWithHttpInfo(resortID, dayID, String.valueOf(startID + random.nextInt(endID - startID + 1)));
        request.increaseSuccessCnt();
      } catch (ApiException e) {
        request.increaseFailCnt();
        logger.error("Get Faild: %s", e.getMessage());
      }
    }

    for (int i = 0; i < numGet; i++) {
      try {
        skiersApi.getSkierResortTotalsWithHttpInfo( String.valueOf(startID + random.nextInt(endID - startID + 1)), new ArrayList(Arrays.asList(resortID)));
        request.increaseSuccessCnt();
      } catch (ApiException e) {
        request.increaseFailCnt();
        logger.error("Get Faild: %s", e.getMessage());
      }
    }

    for (CountDownLatch cnt : counts) {
      cnt.countDown();
    }
  }

  public int getNumPost() {
    return numPost;
  }

  public void setNumPost(int numPost) {
    this.numPost = numPost;
  }

  public int getNumGet() {
    return numGet;
  }

  public void setNumGet(int numGet) {
    this.numGet = numGet;
  }

  public int[] getSkiersIdRange() {
    return skiersIdRange;
  }

  public void setSkiersIdRange(int[] skiersIdRange) {
    this.skiersIdRange = skiersIdRange;
  }

  public int[] getTimeRange() {
    return timeRange;
  }

  public void setTimeRange(int[] timeRange) {
    timeRange = timeRange;
  }

  public Config getConfig() {
    return config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public SkiersApi getSkiersApi() {
    return skiersApi;
  }

  public void setSkiersApi(SkiersApi skiersApi) {
    this.skiersApi = skiersApi;
  }

  public CountDownLatch[] getCounts() {
    return counts;
  }

  public void setCounts(CountDownLatch[] counts) {
    this.counts = counts;
  }
}
