package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public interface PurchaseDao {
    List<Purchase> findPurchases();

    void chekout();//оформить заказ

    Purchase makeAPurchase(Purchase purchase) throws PurchaseException;

    List<Good> addIntoPurchase(int id, int amount) throws RepeatitionException;

    void deleteFromPurchase(int id) throws PurchaseException;

    List<Purchase> findPurchasesByDate(LocalDateTime localDateTime)throws PurchaseNotFoundException;

}
