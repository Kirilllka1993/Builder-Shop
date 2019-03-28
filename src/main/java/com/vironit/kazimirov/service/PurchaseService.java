package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.PurchaseException;


import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseService {

    List<Purchase> findPurchases();


    Purchase createNewPurchase(Client client);

    Purchase findPurchaseById(int purchaseId);

    void makeAPurchase(int purchaseId) throws PurchaseException;

    List<Purchase> findPurchasesByDate(LocalDateTime timeOfPurchase);

    void changeStatus(Purchase purchase, Status status);

    void removePurchase(int purchaseId);


}
