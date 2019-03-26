package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private PurchaseService purchaseService;


    @RequestMapping(value = "/adminPage", method = RequestMethod.GET)
    public void redirectExample(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/adminJsp.jsp").forward(request, response);
    }

    @GetMapping(value = "/back")
    public void back(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @RequestMapping("/showClient")
    public String showForm(ModelMap map) {
        List<Client> clients = adminService.findAllClient();
        map.addAttribute("clients", clients);
        return "adminJsp";
    }

//    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
//    @ResponseBody
//    public String addClient(ClientDto clientDto, ModelMap map) {
//        map.addAttribute("command", clientDto);
//        Client client = new Client();
//        client.setName(clientDto.getName());
//        client.setSurname(clientDto.getSurname());
//        client.setPassword(clientDto.getPassword());
//        client.setLogin(clientDto.getLogin());
//        client.setAddress(clientDto.getAddress());
//        client.setPhoneNumber(clientDto.getPhoneNumber());
//        try{
//            adminService.addClient(client);
//        }catch (RepeatitionException e){
//            return "tryLogin";
//        }
//
//        return "Congratulate! You Add!";
//    }

    @PostMapping("/deleteClient")
    public String deleteClient(@RequestParam("number") int id) {

        adminService.deleteClient(id);
        return "adminJsp";
    }

    @RequestMapping("/login")
    public String findClientByLogin(@RequestParam("login") String login, ModelMap map) {
        Client client = null;
        client = adminService.findClientByLogin(login);
        map.addAttribute("client", client);
        return "adminJsp";
    }

    @RequestMapping("/findById")
    public String findClientById(@RequestParam("idClient") int idClient, ModelMap map) {
        Client client1 = adminService.findClientById(idClient);
        map.addAttribute("client1", client1);
        return "adminJsp";
    }

    @RequestMapping(name = "/updateStatus", method = RequestMethod.POST)
    public String updateStatus(@RequestParam("status") int status,
                               @RequestParam("purchaseId") int purchaseId, ModelMap map) {
        Purchase purchase = purchaseService.findPurchaseById(purchaseId);
        map.addAttribute("purchase", purchase);
        map.addAttribute("status", status);
        adminService.updateStatus(status, purchase);
        return "adminJsp";
    }
}
