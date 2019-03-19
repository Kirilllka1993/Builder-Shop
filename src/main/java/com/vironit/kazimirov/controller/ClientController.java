package com.vironit.kazimirov.controller;


import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.ClientService;
import com.vironit.kazimirov.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@Controller
//@WebServlet("/clientController")
public class ClientController extends HttpServlet {
    @Autowired
    private ClientService clientService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/client.jsp").forward(request,response);

}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clientList = clientService.findAllClients();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String adress = request.getParameter("adress");
        String phoneNumber = request.getParameter("phoneNumber");
        Client client = new Client(0, name, surname, login, password, adress, phoneNumber);
        try {

            clientService.signIn(client);
            clientList=clientService.findAllClients();
            request.setAttribute("clientList",clientList);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/client.jsp").forward(request, response);
        } catch (RepeatitionException e) {
            e.printStackTrace();
        }

    }
}
