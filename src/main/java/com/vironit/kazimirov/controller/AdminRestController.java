package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.ClientDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.log.Log;
import com.vironit.kazimirov.log.MyLogger;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.PurchaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "admin")
public class AdminRestController {
    //private static final Logger LOGGER = Logger.getLogger(AdminRestController.class.getName());
    //private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    @Autowired
    private AdminService adminService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private MyLogger myLogger;

    @RequestMapping(value = "/allClients", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    //@Log
    public List<User> showAllClients() throws InterruptedException {
        //LOGGER.info("the method allClients start");
        List<User> users = adminService.findAllClient();
        //LOGGER.info("the method allClients end");
        return users;
    }

    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int addClient(@RequestBody ClientDto clientDto) throws RepeatitionException {
        User user = clientDto.createClient();
        int clientId = adminService.addClient(user);
        return clientId;
    }

    @RequestMapping(value = "/delete/{clientId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable("clientId") int clientId) {
        adminService.deleteClient(clientId);
    }

    @RequestMapping(value = "/clientBylogin", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User findClientByLogin(@RequestBody ClientDto clientDto) throws ClientNotFoundException {
        User user = adminService.findClientByLogin(clientDto.getLogin());
        return user;
    }

    @RequestMapping(value = "/clientById/{clientId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User findClientById(@PathVariable("clientId") int clientId) throws ClientNotFoundException {
        User user = adminService.findClientById(clientId);
        return user;
    }

//    @RequestMapping(name = "/newStatus", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    public void updateStatus(@RequestBody PurchaseDto purchaseDto) {
//        UserRoleEnum text=purchaseDto.getStatus();
//        System.err.println(text);
//        Status status = Status.valueOf(purchaseDto.getStatus().toUpperCase());
//        Purchase purchase = purchaseService.findPurchaseById(purchaseDto.getId());
//        adminService.updateStatus(status, purchase);
//    }

    @RequestMapping(value = "/newDiscount", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeDiscount(@RequestBody GoodDto goodDto) {
        adminService.changeDiscount(goodDto.getId(), goodDto.getDiscount());
    }
}
