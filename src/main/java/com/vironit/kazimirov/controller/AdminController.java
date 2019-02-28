package com.vironit.kazimirov.controller;

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
import java.util.List;

@WebServlet("/adminController")
public class AdminController extends HttpServlet {
    private AdminService adminService = new AdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clients=adminService.findAllClient();
        request.setAttribute("clients",clients);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/adminJsp.jsp").forward(request, response);
        // response.sendRedirect();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String adress = request.getParameter("adress");
        String phoneNumber = request.getParameter("phoneNumber");
             Client client = new Client(0, name, surname, login, password, adress, phoneNumber);
            List<Client> clients = null;
            try {
                adminService.addClient(client);
                clients=adminService.findAllClient();
                request.setAttribute("clients",clients);
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/adminJsp.jsp").forward(request, response);
            } catch (RepeatitionException e) {
                e.printStackTrace();
            }
    }
}
