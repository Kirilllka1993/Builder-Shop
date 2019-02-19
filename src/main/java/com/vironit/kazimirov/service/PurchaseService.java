package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseService {

    List<Purchase> findPurchases();


    Purchase createNewPurchase(Client client);//не используется нигде

    Purchase makeAPurchase(Purchase purchase) throws PurchaseException;

    Purchase addIntoPurchase(int id, int amount, Purchase purchase) throws RepeatitionException;

    void deleteFromPurchase(int id) throws PurchaseException;

    List<Purchase> findPurchasesByDate(LocalDateTime localDateTime) throws PurchaseNotFoundException;

    List<Good> findGoods();

    Purchase changeStatus (Purchase purchase,Status status);

    void removePurchase(int id) throws PurchaseException;


}
