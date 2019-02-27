package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/adminController")
public class AdminController extends HttpServlet {
    AdminService adminService=new AdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter printWriter=response.getWriter();
//        printWriter.write(     "<html>"+
//                "<title>findByLogin</title>"+
//                "<body>"+
//                "<center>"+
//                "Please enter your login"+
//                "<h2>Page 1</h2>"+"<br>"+
//                "<form method=post action=adminController>"+
//                "<table>"+
//                "<tr>"+
//                "<td>name</td>"+
////                "<td><input type=text name=login></td>"+
//                "<td><input type=text name=name></td>"+"<br>"+
//                "<td>surname</td>"+
//                "<td><input type=text name=surname></td>"+"<br>"+
//                "<td>login</td>"+
//                "<td><input type=text name=login></td>"+"<br>"+
//                "<td>password</td>"+
//                "<td><input type=text name=password></td>"+"<br>"+
//                "<td>adress</td>"+
//                "<td><input type=text name=adress></td>"+"<br>"+
//                "<td>phoneNumber</td>"+
//                "<td><input type=text name=phoneNumber></td>"+"<br>"+
//                "</tr>"+
//                "<tr>"+
//                "<td><input type=reset></td>"+
//                "<td><input type=submit value=submit></td>"+
//                "</tr>"+
//                "</table>"+
//                "<form>"+
//                "</center>"+
//                "</body>"+
//                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter=response.getWriter();
        String name=request.getParameter("name");
        String surname=request.getParameter("surname");
        //String login=(String)request.getAttribute("login");
        String login=request.getParameter("login");
        String password=request.getParameter("password");
        String adress=request.getParameter("adress");
        String phoneNumber=request.getParameter("phoneNumber");
        Client client=new Client(0,name,surname,login,password,adress,phoneNumber);
        List<Client> clients=null;
        try {
            clients=adminService.addClient(client);
            printWriter.write("<html>"+
                    "<title>findByLogin</title>"+
                    "<body>"+
                    clients+
                    "</body>"+
                    "</html>");
        } catch (RepeatitionException e) {
            e.printStackTrace();
        }

        try {
            client = adminService.findClientByLogin(login);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }

        printWriter.write("<html>"+
                    "<title>findByLogin</title>"+
                    "<body>"+
                    client+
                    "</body>"+
                    "</html>");


    }
}