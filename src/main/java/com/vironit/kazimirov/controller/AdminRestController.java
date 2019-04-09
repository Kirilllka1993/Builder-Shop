package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.exception.ClientNotFoundException;
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
    public List<UserDto> showAllClients() {
        List<UserDto> userDtos = adminService.findAllClient();
        return userDtos;
    }

    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int addClient(@RequestBody UserDto userDto) throws RepeatitionException {
//        User user = userDto.createClient();
        int clientId = adminService.addClient(userDto);
        return clientId;
    }

    @RequestMapping(value = "/delete/{clientId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable("clientId") int clientId) {
        adminService.deleteClient(clientId);
    }

    @RequestMapping(value = "/clientBylogin", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDto findClientByLogin(@RequestBody UserDto userDto) throws ClientNotFoundException {
        UserDto clientByLogin = adminService.findClientByLogin(userDto.getLogin());
        return clientByLogin;
    }

    @RequestMapping(value = "/clientById/{clientId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDto findClientById(@PathVariable("clientId") int clientId) throws ClientNotFoundException {
        UserDto userDto = adminService.findClientById(clientId);
        return userDto;
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
