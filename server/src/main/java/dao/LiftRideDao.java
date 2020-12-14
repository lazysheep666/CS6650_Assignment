package dao;
import database.DBCPDataSource;
import java.sql.*;
import model.GetRes;
import model.LiftRide;
import model.LiftRideQuery;
import org.apache.commons.dbcp2.*;

public class LiftRideDao {
  private static BasicDataSource dataSource;

  public LiftRideDao() {
    dataSource = DBCPDataSource.getDataSource();
  }

  public void createLiftRide(LiftRide newLiftRide) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    String insertQueryStatement = "INSERT INTO LiftRides (skierId, resortId, dayId, time, liftId, vertical) " +
        "VALUES (?,?,?,?,?,?)";
    try {
      conn = dataSource.getConnection();
      preparedStatement = conn.prepareStatement(insertQueryStatement);
      preparedStatement.setString(1, newLiftRide.getSkierID());
      preparedStatement.setString(2, newLiftRide.getResortID());
      preparedStatement.setInt(3, newLiftRide.getDayID());
      preparedStatement.setInt(4, newLiftRide.getTime());
      preparedStatement.setString(5, newLiftRide.getLiftID());
      preparedStatement.setInt(6, Integer.valueOf(newLiftRide.getLiftID()) * 10);

      // execute insert SQL statement
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
  }

  public GetRes getLiftRideVertical(LiftRideQuery liftRideQuery) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    GetRes getRes = new GetRes(0);
    String queryStatement = "SELECT SUM(Vertical) FROM LiftRides WHERE ";
    if (liftRideQuery.getSkierID() != null) {
      queryStatement += " SkierId="+ "'" + liftRideQuery.getSkierID() + "'";
    }
    if (liftRideQuery.getDayID() != null) {
      queryStatement += " AND DayID="+ liftRideQuery.getDayID();
    }
    if (liftRideQuery.getResortID() != null) {
      queryStatement += " AND ResortID=" + "'" + liftRideQuery.getResortID() + "'";
    }
    queryStatement += " GROUP BY SkierID";
    try {
      conn = dataSource.getConnection();
      preparedStatement = conn.prepareStatement(queryStatement);
      ResultSet rs = preparedStatement.executeQuery();
      while(rs.next())
      {
        getRes.setTimes(Integer.valueOf(rs.getString(1)));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
    return getRes;
  }

}