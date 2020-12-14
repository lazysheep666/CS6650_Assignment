import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
  private final static String QUEUE_NAME = "Skier_P";
  public static void main(String[] args) {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("sheep");
    factory.setPassword("15223198261");
    factory.setHost("ec2-18-207-126-37.compute-1.amazonaws.com");
    factory.setPort(5672);
    factory.setVirtualHost("/");
    Connection connection = null;
    try {
      connection = factory.newConnection();
      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
      for (int i = 0; i < 10; i++) {
        Consumer consumer = new Consumer(connection, QUEUE_NAME);
        Thread t = new Thread(consumer);
        t.start();
      }
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }

}
