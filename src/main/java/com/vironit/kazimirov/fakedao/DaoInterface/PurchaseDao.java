package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseDao {
    List<Purchase> findPurchases();

    Purchase createNewPurchase(Client client);

    void makeAPurchase(int purchaseId) throws PurchaseException;

    Purchase findPurchaseById(int purchaseId);

    void addIntoPurchase(Good good, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException;

    void deleteFromPurchase(int goodId) throws PurchaseException;

    List<Purchase> findPurchasesByDate(LocalDateTime registration)throws PurchaseNotFoundException;

    void removePurchase(int purchaseId) throws PurchaseException;

    void changeStatus (Purchase purchase, Status status);

}
