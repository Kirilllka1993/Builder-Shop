package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;


import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseService {

    List<Purchase> findPurchases();


    Purchase createNewPurchase(Client client);

    Purchase findPurchaseById(int purchaseId);

    void makeAPurchase(int purchaseId) throws PurchaseException;

    void addIntoPurchase(Good good, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException;

    void deleteFromPurchase(int goodId) throws PurchaseException;

    List<Purchase> findPurchasesByDate(LocalDateTime registration) throws PurchaseNotFoundException;

    void changeStatus(Purchase purchase, Status status);

    void removePurchase(int purchaseId) throws PurchaseException;


}
