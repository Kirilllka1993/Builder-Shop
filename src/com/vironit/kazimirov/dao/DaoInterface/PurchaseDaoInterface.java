package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Purchase;

import java.util.List;

public interface PurchaseDaoInterface {
    List<Purchase> showPurchases();

    void makePurchase();

}
