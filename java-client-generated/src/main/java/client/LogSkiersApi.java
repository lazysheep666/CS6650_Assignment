package client;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import io.swagger.client.model.SkierVertical;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class LogSkiersApi extends SkiersApi {

  String fileName = "record.csv";
  FileWriter fw;
  BufferedWriter bw;
  PrintWriter pw;

  public LogSkiersApi() {
    try {
      fw = new FileWriter(fileName, true);
      bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public LogSkiersApi(ApiClient apiClient) {
    super(apiClient);
  }


  @Override
  public ApiClient getApiClient() {
    return super.getApiClient();
  }

  @Override
  public ApiResponse<SkierVertical> getSkierDayVerticalWithHttpInfo(String resortID, String dayID,
      String skierID) throws ApiException {
    long startTime = System.currentTimeMillis();
    ApiResponse<SkierVertical> res = super.getSkierDayVerticalWithHttpInfo(resortID, dayID, skierID);
    long endTime = System.currentTimeMillis();
    synchronized (LogSkiersApi.class) {
      writeRecord(startTime, "GET", endTime - startTime, res.getStatusCode());
    }
    return res;
  }

  @Override
  public ApiResponse<Void> writeNewLiftRideWithHttpInfo(LiftRide body) throws ApiException {
    long startTime = System.currentTimeMillis();
    ApiResponse<Void> res = super.writeNewLiftRideWithHttpInfo(body);
    long endTime = System.currentTimeMillis();
    synchronized (LogSkiersApi.class) {
      writeRecord(startTime, "POST", endTime - startTime, res.getStatusCode());
    }
    return res;
  }


  public void writeRecord(long startTime, String type, long latency, int code) {
      pw.println("" + new Date(startTime) + "," + type + ","+ latency + "," + code);
  }

  public void done() {
    pw.flush();
  }

}
