package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    List<Purchase> showPurchases();

    void chekout();//оформить заказ

    void makeAPurchase();

    List<Good> addIntoPurchase(int id, int amount);

    void deleteFromPurchase(int id);


}
