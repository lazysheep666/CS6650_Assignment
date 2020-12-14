package client;

public class RequestCount {
  int successCount = 0;
  int failCount = 0;

  synchronized public int getSuccessCount() {
    return successCount;
  }

  synchronized public void increaseSuccessCnt() {
    this.successCount++;
  }

  synchronized public int getFailCount() {
    return failCount;
  }

  synchronized public void increaseFailCnt() {
    this.failCount++;
  }
}
