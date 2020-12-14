import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import dao.LiftRideDao;
import exceptions.InvalidRequestBodyException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeoutException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LiftRide;
import model.LiftRideQuery;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import utils.Util;

@WebServlet(name = "SkierServlet")
public class SkierServlet extends HttpServlet {

  private final static String QUEUE_NAME = "Skier_P";
  private Connection conn;
  @Override
  public void init() throws ServletException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("sheep");
    factory.setPassword("15223198261");
    factory.setHost("ec2-18-207-126-37.compute-1.amazonaws.com");
    factory.setPort(5672);
    factory.setVirtualHost("/");
    try {
      conn = factory.newConnection();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("application/json; charset=utf8");
    String urlPath = req.getPathInfo();
    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("No Param");
      return;
    }

    urlPath = urlPath.substring(1);
    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)
    LiftRideQuery liftRideQuery = new LiftRideQuery();
    if (Util.isUrlValid(urlParts, new String[]{"{resortID}", "days", "{dayID}", "skiers", "{skierID}"}, new HashSet<Integer>(
        Arrays.asList(0, 2, 4)))) {
      try {
        liftRideQuery = Util.getParams(urlParts);
      } catch (InvalidRequestBodyException e) {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        res.getWriter().write("Bad Request!");
        return;
      }
    } else if (Util.isUrlValid(urlParts, new String[]{"{skierID}", "vertical"}, new HashSet<Integer>(Arrays.asList(0)))){
      try {
        liftRideQuery = Util.getParams(urlParts, req);
      } catch (InvalidRequestBodyException e) {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        res.getWriter().write("Bad Request!");
        return;
      }
    } else {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("Data Source Is Not Found!");
      return;
    }
    res.setStatus(HttpServletResponse.SC_OK);
    LiftRideDao liftRideDao = new LiftRideDao();
    res.getWriter().write(new Gson().toJson(liftRideDao.getLiftRideVertical(liftRideQuery)));
    System.out.println("get success!");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    res.setContentType("application/json; charset=utf8");
    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("No Param");
      return;
    }

    urlPath = urlPath.substring(1);
    String[] urlParts = urlPath.split("/");
    if (!Util.isUrlValid(urlParts, new String[]{"liftrides"}, new HashSet<Integer>())) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("Data Source Is Not Found!");
    } else {
      LiftRide liftRide = new LiftRide();
      try {
        liftRide = Util.getBody(req);
      } catch (InvalidRequestBodyException e) {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        res.getWriter().write("Bad Request!");
      }
      try {
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, liftRide.getByteArray());
        System.out.println(" [x] Sent '" + liftRide + "'");
        channel.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      res.setStatus(HttpServletResponse.SC_OK);
      res.getWriter().write("It works!");
      System.out.println("post success!");
    }
  }

}
