package com.vironit.kazimirov.controller;

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
@RequestMapping(value = "purchase")
public class PurchaseRestController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/allPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Purchase> showAllPurchases() {
        List<Purchase> purchaseList = purchaseService.findPurchases();
        return purchaseList;
    }

    @RequestMapping(value = "/createPurchase/{clientId}",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int createPurchase(@PathVariable ("clientId")int clientId) throws ClientNotFoundException {
        User user =adminService.findClientById(clientId);
        int purchaseId=purchaseService.createNewPurchase(user);
        return purchaseId;

    }

    @RequestMapping(value = "/findPurchase/{purchaseId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Purchase findPurchaseById(@PathVariable ("purchaseId")int purchaseId) {
       return purchaseService.findPurchaseById(purchaseId);
    }

    @RequestMapping(value = "/makePurchase/{purchaseId}",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeAPurchase(@PathVariable ("purchaseId")int purchaseId) throws PurchaseException {
        purchaseService.makeAPurchase(purchaseId);
    }

    @RequestMapping(value = "/delete/{purchaseId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePurchase(@PathVariable ("purchaseId")int purchaseId) throws PurchaseException {
        purchaseService.removePurchase(purchaseId);
    }

//    @RequestMapping(value = "newStatus",method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void changeStatus(@PathVariable ("purchaseId")int purchaseId) throws PurchaseException {
//        purchaseService.removePurchase(purchaseId);
//    }
}
