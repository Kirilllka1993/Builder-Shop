package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public interface PurchaseDao {
    List<Purchase> showPurchases();

    void chekout();//оформить заказ

    Purchase makeAPurchase(List<Good> goods, Client client, LocalDateTime registration, LocalDateTime purchase, String status);

    List<Good> addIntoPurchase(int id, int amount) throws RepeatitionException;

    void deleteFromPurchase(int id);

    List<Purchase> searchPurchasesByDate(LocalDateTime localDateTime)throws PurchaseNotFoundException;

}
