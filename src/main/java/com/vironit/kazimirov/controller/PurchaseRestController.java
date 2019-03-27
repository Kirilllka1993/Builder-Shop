package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "purchase")
public class PurchaseRestController {
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/allPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Purchase> showAllPurchases() {
        List<Purchase> purchaseList = purchaseService.findPurchases();
        return purchaseList;
    }
}
