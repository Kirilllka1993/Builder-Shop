package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.PurchaseException;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseDao {
    List<Purchase> findPurchases();

    int createNewPurchase(User user);

    void makeAPurchase(int purchaseId) throws PurchaseException;

    Purchase findPurchaseById(int purchaseId);

    List<Purchase> findPurchasesByDate(LocalDateTime timeOfPurchase);

    void removePurchase(int purchaseId);

    void changeStatus (Purchase purchase, Status status);

}
