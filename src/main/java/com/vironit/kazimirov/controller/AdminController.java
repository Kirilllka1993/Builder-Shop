package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.daoJdbc.AdminDaoImplJdbs;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/adminController")
public class AdminController extends HttpServlet {
    private AdminService adminService = new AdminServiceImpl();
    //private AdminDaoImplJdbs adminDaoImplJdbs=new AdminDaoImplJdbs();
   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clients = adminService.findAllClient();
        request.setAttribute("clients", clients);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/adminJsp.jsp").forward(request, response);
        // response.sendRedirect();
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String URL = "jdbc:mysql://localhost:3306/shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "1111";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("SqlException ");
        }
        AdminDaoImplJdbs adminDaoImplJdbs=new AdminDaoImplJdbs(connection);
        Client client=null;
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String adress = request.getParameter("adress");
        String phoneNumber = request.getParameter("phoneNumber");


            client=new Client(0, name, surname, login, password, adress, phoneNumber);
        try {
            adminDaoImplJdbs.addClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            adminDaoImplJdbs.deleteClient("2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//            try {
//                adminService.addClient(client);
//                clients=adminService.findAllClient();
//                request.setAttribute("clients",clients);
//                getServletContext().getRequestDispatcher("/WEB-INF/jsp/adminJsp.jsp").forward(request, response);
//            } catch (RepeatitionException e) {
//                e.printStackTrace();
//            }
    }
}
