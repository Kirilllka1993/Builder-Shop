package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop")
public class PurchaseRestController {
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "admin/purchase/allPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseDto> showAllPurchases() {
        List<PurchaseDto> purchasesDtos = purchaseService.findPurchases();
        return purchasesDtos;
    }

    @RequestMapping(value = "purchase/createPurchase",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int createPurchase(@RequestBody UserDto userDto) throws ClientNotFoundException {
        int purchaseId=purchaseService.createNewPurchase(userDto);
        return purchaseId;

    }

    @RequestMapping(value = "admin/purchase/findPurchase/{purchaseId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PurchaseDto findPurchaseById(@PathVariable ("purchaseId")int purchaseId) throws PurchaseNotFoundException {
       return purchaseService.findPurchaseById(purchaseId);
    }

    @RequestMapping(value = "purchase/makePurchase/{purchaseId}",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void makeAPurchase(@PathVariable ("purchaseId")int purchaseId) throws PurchaseException, PurchaseNotFoundException {
        purchaseService.makeAPurchase(purchaseId);
    }

    @RequestMapping(value = "admin/purchase/delete/{purchaseId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removePurchase(@PathVariable ("purchaseId")int purchaseId) throws PurchaseException, PurchaseNotFoundException, CantDeleteElement {
        purchaseService.removePurchase(purchaseId);
    }

    @RequestMapping(value = "admin/purchase/findByDate/",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseDto> findPurchaseByDate(@RequestBody PurchaseDto purchaseDto){
        return purchaseService.findPurchasesByDate(purchaseDto.getTimeOfPurchase());
    }

    @RequestMapping(value = "purchase/newStatus",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeStatus(@RequestBody PurchaseDto purchaseDto) throws PurchaseNotFoundException {
        Status status=Status.valueOf(purchaseDto.getStatus());
        purchaseService.changeStatus(purchaseDto,status);
    }
}
