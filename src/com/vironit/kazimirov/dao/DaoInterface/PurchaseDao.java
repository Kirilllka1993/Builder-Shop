package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Purchase;

import java.util.List;

public interface PurchaseDao {
    List<Purchase> showPurchases();

    void makePurchase();

}
