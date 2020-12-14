package client1;

import client.Caculator;
import client.ClientThread;
import client.Config;
import client.LoadPropertiesFile;
import client.LogSkiersApi;
import client.RequestCount;
import client.Runner;
import io.swagger.client.ApiClient;
import io.swagger.client.api.SkiersApi;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Main {

  public static void main(String[] args) {
    Runner runner = new Runner();
    runner.run(new SkiersApi());
  }
}
