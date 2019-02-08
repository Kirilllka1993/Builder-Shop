package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;

import java.util.List;

public interface PurchaseDao {
    void chekout();//оформить заказ

    void makeAPurchase();

    void addIntoPurchase(Good good);

    void deleteFromPurchase(Good good);

}
