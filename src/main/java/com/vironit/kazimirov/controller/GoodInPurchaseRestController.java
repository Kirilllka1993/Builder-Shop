package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.GoodInPurchaseDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.GoodInPurchase;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.GoodInPurchaseService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "goodInPurchase")
public class GoodInPurchaseRestController {
    @Autowired
    private GoodInPurchaseService goodInPurchaseService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addGoodInPurchase(@RequestBody GoodInPurchaseDto goodInPurchaseDto) throws GoodNotFoundException, RepeatitionException, PurchaseException {
        Good good = goodService.findGoodById(goodInPurchaseDto.getGoodId());
        Purchase purchase = purchaseService.findPurchaseById(goodInPurchaseDto.getPurchaseId());
        goodInPurchaseService.addInGoodInPurchase(good, goodInPurchaseDto.getAmount(), purchase);
    }

    @RequestMapping(value = "/allGoodInPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodInPurchase> showAllGoodInPurchases() {
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        return goodInPurchases;
    }

    @RequestMapping(value = "/deleteFromCart", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFromCart(@RequestBody GoodInPurchaseDto goodInPurchaseDto) throws GoodNotFoundException, PurchaseException {
        Good good = goodService.findGoodById(goodInPurchaseDto.getGoodId());
        Purchase purchase = purchaseService.findPurchaseById(goodInPurchaseDto.getPurchaseId());
        goodInPurchaseService.deleteFromPurchase(good, purchase);
    }

    @RequestMapping(value = "/{goodInPurchaseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGoodInPurchase(@PathVariable("goodInPurchaseId") int goodInPurchaseId) {
        goodInPurchaseService.deleteGoodInPurchase(goodInPurchaseId);
    }

    @RequestMapping(value = "/findGoods", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Good> findGoodsInPurchase(@RequestBody GoodInPurchaseDto goodInPurchaseDto) {
        List<Good> goods = goodInPurchaseService.findGoodsByPurchase(goodInPurchaseDto.getPurchaseId());
        return goods;
    }

    @RequestMapping(value = "/findPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Purchase> findPurchasesByGood(@RequestBody GoodInPurchaseDto goodInPurchaseDto) {
        List<Purchase> purchases = goodInPurchaseService.findPurchasesByGood(goodInPurchaseDto.getGoodId());
        return purchases;
    }

    @RequestMapping(value = "/deleteWithCancelled", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePurchase(@RequestBody GoodInPurchaseDto goodInPurchaseDto) {
        Purchase purchase = purchaseService.findPurchaseById(goodInPurchaseDto.getPurchaseId());
        goodInPurchaseService.deleteGoodInPurchasesWithCancelledStatus(purchase);
    }

    @RequestMapping(value = "/newAmount", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAmount(@RequestBody GoodInPurchaseDto goodInPurchaseDto) throws PurchaseException {
        goodInPurchaseService.changeAmountInGoodInPurchase(goodInPurchaseDto.getGoodId(),goodInPurchaseDto.getAmount(),goodInPurchaseDto.getPurchaseId());
    }

    @RequestMapping(value = "/goodInPurchase", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GoodInPurchase findGoodInPurchaseByGoodAndPurchase(@RequestBody GoodInPurchaseDto goodInPurchaseDto){
        return goodInPurchaseService.findGoodInPurchase(goodInPurchaseDto.getGoodId(),goodInPurchaseDto.getPurchaseId());
    }


    @RequestMapping(value = "/goodInPurchase/{goodInPurchaseId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GoodInPurchase findGoodInPurchase(@PathVariable("goodInPurchaseId") int goodInPurchaseId){
        return goodInPurchaseService. findGoodInPurchaseById(goodInPurchaseId);
    }

    @RequestMapping(value = "/oldAmountOfGood/{goodInPurchaseId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnedAmountOfGood(@PathVariable("goodInPurchaseId") int goodInPurchaseId){
        GoodInPurchase goodInPurchase=goodInPurchaseService. findGoodInPurchaseById(goodInPurchaseId);
        goodInPurchaseService.returnedAmountOfGood(goodInPurchase);
    }

    @RequestMapping(value = "/newAmountOfGood/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reduceAmount(@RequestBody GoodInPurchaseDto goodInPurchaseDto){
        goodInPurchaseService.reduceAmount(goodInPurchaseDto.getGoodId(),goodInPurchaseDto.getAmount());
    }

    @RequestMapping(value = "/allCartByPurchase/{purchaseId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodInPurchase> findGoodInPurchasesByPurchase(@PathVariable("purchaseId") int purchaseId){
        List<GoodInPurchase> goodInPurchases=goodInPurchaseService.findGoodInPurchasesByPurchase(purchaseId);
        return goodInPurchases;
    }

    @RequestMapping(value = "/allCartByGood/{goodId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodInPurchase> findGoodInPurchasesByGood(@PathVariable("goodId") int goodId){
        List<GoodInPurchase> goodInPurchases=goodInPurchaseService.findGoodInPurchasesByGood(goodId);
        return goodInPurchases;
    }


}
