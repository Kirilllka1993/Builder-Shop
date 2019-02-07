package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    List<Purchase> showPurchases();

    void makePurchase();


}
