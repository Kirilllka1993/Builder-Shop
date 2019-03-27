package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.ClientDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "admin")
public class AdminRestController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/allClients", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Client> showAllClients() {
        List<Client> clients = adminService.findAllClient();
        return clients;
    }

    @RequestMapping(value = "/newClient", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addClient(@RequestBody ClientDto clientDto) {
        Client client = clientDto.createClient();
        try {
            adminService.addClient(client);
        } catch (RepeatitionException e) {
        }
    }

    @RequestMapping(value = "/{clientId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable("clientId") int clientId) {
        adminService.deleteClient(clientId);
    }

    @RequestMapping(value = "/clientBylogin", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Client findClientByLogin(@RequestBody ClientDto clientDto) {
        Client client = adminService.findClientByLogin(clientDto.getLogin());
        return client;
    }

    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Client findClientById(@PathVariable("clientId") int clientId) {
        Client client = adminService.findClientById(clientId);
        return client;
    }

    @RequestMapping(name = "/newStatus", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestBody PurchaseDto purchaseDto) {
        Purchase purchase = purchaseService.findPurchaseById(5);
        adminService.updateStatus(purchaseDto.getStatus(), purchase);
//        Purchase purchase = purchaseService.findPurchaseById(id);не выполнил
//        adminService.updateStatus(status, purchase);
    }

    @RequestMapping(value="/newDiscount",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeDiscount(@RequestBody GoodDto goodDto){
        adminService.changeDiscount(goodDto.getId(),goodDto.getDiscount());
    }
}
