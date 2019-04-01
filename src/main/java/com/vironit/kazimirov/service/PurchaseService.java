package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.PurchaseException;


import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseService {

    List<Purchase> findPurchases();//выполнено


    int createNewPurchase(Client client);//выполнено

    Purchase findPurchaseById(int purchaseId);//выполнено

    void makeAPurchase(int purchaseId) throws PurchaseException;//выполнено

    List<Purchase> findPurchasesByDate(LocalDateTime timeOfPurchase);

    void changeStatus(Purchase purchase, Status status);

    void removePurchase(int purchaseId);//выполнено


}
