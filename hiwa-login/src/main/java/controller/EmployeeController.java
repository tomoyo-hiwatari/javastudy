package controller;
 
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.EmployeeBean;
import service.EmployeeService;
 
public class EmployeeController extends HttpServlet {
 public void doPost(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, IOException {
 
 try {
 String id = request.getParameter("id");
 String password = request.getParameter("password");
 
 EmployeeService employeeService = new EmployeeService();
 
 EmployeeBean employeeBean = new EmployeeBean();
 
 employeeBean = employeeService.search(id,password);
 
 request.setAttribute("EmployeeBean", employeeBean);
 
 } catch (Exception e) {
 e.printStackTrace();
 } finally {
 ServletContext context = this.getServletContext();
 RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
 dispatcher.forward(request, response);
 }
 }
}