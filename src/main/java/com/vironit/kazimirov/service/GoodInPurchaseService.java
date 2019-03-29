package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.GoodInPurchase;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface GoodInPurchaseService {

    void addInGoodInPurchase(Good good, int amount, Purchase purchase) throws RepeatitionException, PurchaseException;//выполнено

    List<GoodInPurchase> findGoodInPurchases();//выполнено

    void deleteFromPurchase(Good good, Purchase purchase) throws PurchaseException;//выполнено

    void deleteGoodInPurchase(int goodInPurchase);//выполнено

    List<Good> findGoodsByPurchase(int purchaseId);//выполнено

    List<Purchase> findPurchasesByGood(int goodId);//выполнено

    void deleteGoodInPurchasesWithCancelledStatus(Purchase purchase);//выполнено

    void changeAmountInGoodInPurchase (int goodId, int amount, int purchaseId) throws PurchaseException;//выполнено

    GoodInPurchase findGoodInPurchase (int goodId, int purchaseId);//выполнено

    GoodInPurchase findGoodInPurchaseById(int goodInPurchaseId);//выполнено

    void returnedAmountOfGood(GoodInPurchase goodInPurchase);//выполнено

    void reduceAmount(int goodId, int amount);//выполнено

    List<GoodInPurchase> findGoodInPurchasesByPurchase(int purchaseId);//выполнено

    List<GoodInPurchase> findGoodInPurchasesByGood(int goodId);//выполнено


}
