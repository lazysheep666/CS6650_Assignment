package client;

public class Config {
  int MAX_THREADS = 256;
  int SKIER_NUMBER = 50000;
  int LIFTS_NUMBER = 40;
  int SKI_DAY = 1;
  String RESORT_NAME = "SilverMt";
  String IP = "0.0.0.0";
  String PORTAL = "8080";
  String BASE_DIR = "LAB2_war_exploded";

  public Config() {

  }

  public int getMAX_THREADS() {
    return MAX_THREADS;
  }

  public void setMAX_THREADS(int MAX_THREADS) {
    this.MAX_THREADS = MAX_THREADS;
  }

  public int getSKIER_NUMBER() {
    return SKIER_NUMBER;
  }

  public void setSKIER_NUMBER(int SKIER_NUMBER) {
    this.SKIER_NUMBER = SKIER_NUMBER;
  }

  public int getLIFTS_NUMBER() {
    return LIFTS_NUMBER;
  }

  public void setLIFTS_NUMBER(int LIFTS_NUMBER) {
    this.LIFTS_NUMBER = LIFTS_NUMBER;
  }

  public int getSKI_DAY() {
    return SKI_DAY;
  }

  public void setSKI_DAY(int SKI_DAY) {
    this.SKI_DAY = SKI_DAY;
  }

  public String getRESORT_NAME() {
    return RESORT_NAME;
  }

  public void setRESORT_NAME(String RESORT_NAME) {
    this.RESORT_NAME = RESORT_NAME;
  }

  public String getIP() {
    return IP;
  }

  public void setIP(String IP) {
    this.IP = IP;
  }

  public String getPORTAL() {
    return PORTAL;
  }

  public void setPORTAL(String PORTAL) {
    this.PORTAL = PORTAL;
  }

  public String getBASE_DIR() {
    return BASE_DIR;
  }

  public void setBASE_DIR(String BASE_DIR) {
    this.BASE_DIR = BASE_DIR;
  }
}
