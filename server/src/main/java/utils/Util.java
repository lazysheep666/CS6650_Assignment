package utils;

import com.google.gson.Gson;
import exceptions.InvalidRequestBodyException;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import model.LiftRide;
import model.LiftRideQuery;

public class Util {
  public static boolean isUrlValid(String[] urlPath, String[] targetUrl, Set<Integer> partterns) {
    if (urlPath.length != targetUrl.length) {
      return false;
    }
    for (int i = 0; i < urlPath.length; i++) {
      if (!urlPath[i].equals(targetUrl[i]) && !partterns.contains(i)) {
        return false;
      }
    }
    return true;
  }
  public static LiftRide getBody(HttpServletRequest req) throws InvalidRequestBodyException {
    LiftRide liftRide = new LiftRide();
    try {
      liftRide = new Gson().fromJson(req.getReader(), LiftRide.class);
    } catch (Exception e) {
      throw new InvalidRequestBodyException("Bad Request Body");
    }
    if (liftRide.getDayID() == null || liftRide.getLiftID() == null || liftRide.getResortID() == null || liftRide.getSkierID() == null || liftRide.getTime() == null) {
      throw new InvalidRequestBodyException("Bad Request Body");
    }
    return liftRide;
  }

  public static LiftRideQuery getParams(String[] urlParts) throws InvalidRequestBodyException {
    LiftRideQuery liftRideQuery = new LiftRideQuery();
    try {
      liftRideQuery.setResortID(urlParts[0]);
      liftRideQuery.setDayID(Integer.valueOf(urlParts[2]));
      liftRideQuery.setSkierID(urlParts[4]);
    } catch (Exception e) {
      throw new InvalidRequestBodyException("Bad Request");
    }
    return liftRideQuery;
  }

  public static LiftRideQuery getParams(String[] urlParts, HttpServletRequest req) throws InvalidRequestBodyException {
    LiftRideQuery liftRideQuery = new LiftRideQuery();
    liftRideQuery.setSkierID(urlParts[0]);
    if (req.getParameter("resort") == null) {
      throw new InvalidRequestBodyException("Bad Request");
    }
    liftRideQuery.setResortID(req.getParameter("resort"));
    return liftRideQuery;
  }
}
