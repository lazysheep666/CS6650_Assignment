package model;

public class PostRes {
  private boolean success;
  private String info;
  PostRes(boolean success) {
    this.success = success;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
