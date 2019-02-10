package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;

import java.util.List;

public interface PurchaseDao {
    List<Purchase> showPurchases();

    void chekout();//оформить заказ

    void makeAPurchase();

    List<Good> addIntoPurchase(int id, int amount);

    void deleteFromPurchase(int id);

}
