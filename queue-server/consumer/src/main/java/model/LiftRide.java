package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LiftRide implements Serializable {
  private static final long serialVersionUID = 6529685098267757690L;
  private String resortID;
  private Integer dayID;
  private String skierID;
  private Integer time;
  private String liftID;

  public String getResortID() {
    return resortID;
  }

  public void setResortID(String resortID) {
    this.resortID = resortID;
  }

  public Integer getDayID() {
    return dayID;
  }

  public void setDayID(Integer dayID) {
    this.dayID = dayID;
  }

  public String getSkierID() {
    return skierID;
  }

  public void setSkierID(String skierID) {
    this.skierID = skierID;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public String getLiftID() {
    return liftID;
  }

  public void setLiftID(String liftID) {
    this.liftID = liftID;
  }

  public byte[] getByteArray() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(outputStream);
    os.writeObject(this);
    outputStream.close();
    os.close();
    return outputStream.toByteArray();
  }
}
