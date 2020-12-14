package model;

public class LiftRideQuery {
  private String resortID;
  private String skierID;
  private Integer dayID;

  public String getResortID() {
    return resortID;
  }

  public void setResortID(String resortID) {
    this.resortID = resortID;
  }

  public String getSkierID() {
    return skierID;
  }

  public void setSkierID(String skierID) {
    this.skierID = skierID;
  }

  public Integer getDayID() {
    return dayID;
  }

  public void setDayID(Integer dayID) {
    this.dayID = dayID;
  }
}
