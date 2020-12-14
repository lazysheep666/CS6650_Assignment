package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPropertiesFile {

  public static void load(Config config) throws FileNotFoundException, IOException {
    InputStream inputStream = new FileInputStream(new File("src/main/resources/config.properties"));
    Properties properties = new Properties();
    properties.load(inputStream);
    if (properties.get("MAX_THREADS") != null) {
      config.setMAX_THREADS(Integer.parseInt((String)properties.get("MAX_THREADS")));
    }
    if (properties.get("SKIER_NUMBER") != null) {
      config.setSKIER_NUMBER(Integer.parseInt((String)properties.get("SKIER_NUMBER")));
    }
    if (properties.get("LIFTS_NUMBER") != null) {
      config.setLIFTS_NUMBER(Integer.parseInt((String)properties.get("LIFTS_NUMBER")));
    }
    if (properties.get("SKI_DAY") != null) {
      config.setSKI_DAY(Integer.parseInt((String)properties.get("SKI_DAY")));
    }
    if (properties.get("RESORT_NAME") != null) {
      config.setRESORT_NAME((String)properties.get("RESORT_NAME"));
    }
    if (properties.get("IP") != null) {
      config.setIP((String)properties.get("IP"));
    }
    if (properties.get("PORTAL") != null) {
      config.setPORTAL((String)properties.get("PORTAL"));
    }
    if (properties.get("BASE_DIR") != null) {
      config.setBASE_DIR((String)properties.get("BASE_DIR"));
    }
  }
}
