import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import dao.LiftRideDao;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import model.LiftRide;

public class Consumer implements Runnable{

  private Connection conn;
  private String QUEUE_NAME;
  public Consumer(Connection conn, String QUEUE_NAME) {
    this.conn = conn;
    this.QUEUE_NAME = QUEUE_NAME;
  }

  @Override
  public void run() {
    Channel channel = null;
    try {
      channel = conn.createChannel();
      LiftRideDao liftRideDao = new LiftRideDao();
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);

      DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        byte[] byteArray = delivery.getBody();
        LiftRide liftRide = null;
        try {
          liftRide = (LiftRide) deserialize(byteArray);
          System.out.println("receive " + liftRide);
          liftRideDao.createLiftRide(liftRide);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      };
      channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Object deserialize(byte[] byteArray) throws IOException, ClassNotFoundException {
    ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
    ObjectInputStream is = new ObjectInputStream(in);
    return is.readObject();
  }
}
