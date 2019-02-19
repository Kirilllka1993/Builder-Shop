package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public interface PurchaseDao {
    List<Purchase> findPurchases();

    Purchase createNewPurchase(Client client);

    Purchase makeAPurchase(Purchase purchase) throws PurchaseException;

    Purchase addIntoPurchase(int id, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException;

    void deleteFromPurchase(int id) throws PurchaseException;

    List<Purchase> findPurchasesByDate(LocalDateTime localDateTime)throws PurchaseNotFoundException;

    List<Good> findGoods();

    void removePurchase(int id) throws PurchaseException;

    Purchase changeStatus (Purchase purchase, Status status);

}
