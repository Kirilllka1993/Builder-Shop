package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(value = "purchase")
public class PurchaseRestController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "admin/purchase/allPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseDto> showAllPurchases() {
        List<PurchaseDto> purchasesDtos = purchaseService.findPurchases();
        return purchasesDtos;
    }

    @RequestMapping(value = "purchase/createPurchase/{clientId}",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int createPurchase(@PathVariable ("clientId")UserDto userDto) throws ClientNotFoundException {
        //User user =adminService.findClientById(userId);
        int purchaseId=purchaseService.createNewPurchase(userDto);
        return purchaseId;

    }

    @RequestMapping(value = "admin/purchase/findPurchase/{purchaseId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PurchaseDto findPurchaseById(@PathVariable ("purchaseId")int purchaseId) {
       return purchaseService.findPurchaseById(purchaseId);
    }

    @RequestMapping(value = "purchase/makePurchase/{purchaseId}",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void makeAPurchase(@PathVariable ("purchaseId")int purchaseId) throws PurchaseException {
        purchaseService.makeAPurchase(purchaseId);
    }

    @RequestMapping(value = "admin/purchase/delete/{purchaseId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removePurchase(@PathVariable ("purchaseId")int purchaseId) throws PurchaseException {
        purchaseService.removePurchase(purchaseId);
    }

//    @RequestMapping(value = "newStatus",method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void changeStatus(@PathVariable ("purchaseId")int purchaseId) throws PurchaseException {
//        purchaseService.removePurchase(purchaseId);
//    }
}
