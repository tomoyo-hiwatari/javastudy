package service;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bean.EmployeeBean;
 
public class EmployeeService {

 private static final String POSTGRES_DRIVER = "org.postgresql.Driver";

 private static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5432/Employee";

 private static final String USER = "postgres";

 private static final String PASS = "postgres";

 private static final String TIME_FORMAT = "yyyy/MM/dd hh:mm:ss";
 
 EmployeeBean employeeData = null;
 
 public EmployeeBean search(String id, String password) {
 
 Connection connection = null;
 Statement statement = null;
 ResultSet resultSet = null;
 
 try {
 Class.forName(POSTGRES_DRIVER);
 connection = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS);
 statement = connection.createStatement();
 
 Calendar cal = Calendar.getInstance();
 SimpleDateFormat sdFormat = new SimpleDateFormat(TIME_FORMAT);
 String login_time = sdFormat.format(cal.getTime());
 
 String SQL_UPDATE = "UPDATE employee_table SET login_time = ? where id = ?";
 PreparedStatement psExecuteUpdate = connection.prepareStatement(SQL_UPDATE);
 psExecuteUpdate.setString(1, login_time);
 psExecuteUpdate.setString(2, id);
 psExecuteUpdate.executeUpdate();
 
 String SELECT_SQL = "select * from employee_table WHERE id = ? and password = ? ";

 PreparedStatement psExecuteQuery = connection.prepareStatement(SELECT_SQL);
 psExecuteQuery.setString(1, id);
 psExecuteQuery.setString(2, password);
 resultSet = psExecuteQuery.executeQuery();
 
 while (resultSet.next()) {

 String column1 = resultSet.getString("name");
 String column2 = resultSet.getString("comment");
 String column3 = resultSet.getString("login_time");
 

 employeeData = new EmployeeBean();
 employeeData.setName(column1);
 employeeData.setComment(column2);
 employeeData.setLogin_Time(column3);
 }
 
 } catch (ClassNotFoundException e) {
 e.printStackTrace();
 
 } catch (SQLException e) {
 e.printStackTrace();
 
 } finally {
 try {
 
 if (resultSet != null) {
 resultSet.close();
 }
 if (statement != null) {
 statement.close();
 }
 if (connection != null) {
 connection.close();
 }
 
 } catch (SQLException e) {
 e.printStackTrace();
 }
 }
 return employeeData;
 }
}