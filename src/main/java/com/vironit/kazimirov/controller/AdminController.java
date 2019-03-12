package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;



    @RequestMapping(value = "/adminPage", method = RequestMethod.GET)
    public void redirectExample(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/adminJsp.jsp").forward(request, response);
    }

    @GetMapping(value = "/back")
    public void back(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @RequestMapping("/showClient")
    public String showForm(ModelMap map) throws SQLException {
        List<Client> clients = adminService.findAllClient();
        map.addAttribute("clients", clients);
        return "adminJsp";
    }

    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
    @ResponseBody
    public String addClient(@RequestParam("name") String name,
                            @RequestParam("surname") String surname,
                            @RequestParam("login") String login,
                            @RequestParam("password") String password,
                            @RequestParam("adress") String adress,
                            @RequestParam("phoneNumber") String phoneNumber) throws RepeatitionException, SQLException {
        Client client = new Client();
        client.setName(name);
        client.setSurname(surname);
        client.setLogin(login);
        client.setPassword(password);
        client.setAddress(adress);
        client.setPhoneNumber(phoneNumber);
        adminService.addClient(client);
        return "Congratulate! You Add!";
    }

    @PostMapping ("/deleteClient")
    public String deleteClient (@RequestParam("number") int id) throws SQLException {

        adminService.deleteClient(id);
        return "adminJsp";
    }
}
