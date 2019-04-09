package com.vironit.kazimirov.controller.controllerWeb;

import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
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
        List<UserDto> userDtos = adminService.findAllClient();
        map.addAttribute("clients", userDtos);
        return "adminJsp";
    }

//    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
//    @ResponseBody
//    public UserRoleEnum addClient(UserDto clientDto, ModelMap map) {
//        map.addAttribute("command", clientDto);
//        User user = new User();
//        user.setName(clientDto.getName());
//        user.setSurname(clientDto.getSurname());
//        user.setPassword(clientDto.getPassword());
//        user.setLogin(clientDto.getLogin());
//        user.setAddress(clientDto.getAddress());
//        user.setPhoneNumber(clientDto.getPhoneNumber());
//        try{
//            adminService.addClient(user);
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
    public String findClientByLogin(@RequestParam("login") String login, ModelMap map) throws ClientNotFoundException {
        UserDto clientByLogin = adminService.findClientByLogin(login);
        map.addAttribute("client", clientByLogin);
        return "adminJsp";
    }

    @RequestMapping("/findById")
    public String findClientById(@RequestParam("idClient") int idClient, ModelMap map) throws ClientNotFoundException {
        UserDto userDto = adminService.findClientById(idClient);
        map.addAttribute("client1", userDto);
        return "adminJsp";
    }

    @RequestMapping(name = "/updateStatus", method = RequestMethod.POST)
    public String updateStatus(@RequestParam("status") Status status,
                               @RequestParam("purchaseId") int purchaseId, ModelMap map) {
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(purchaseId);
        map.addAttribute("purchase", purchaseDto);
        map.addAttribute("status", status);
        adminService.updateStatus(status, purchaseDto);
        return "adminJsp";
    }
}
