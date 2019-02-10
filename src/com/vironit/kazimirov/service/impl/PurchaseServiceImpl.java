package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.PurchaseDaoImpl;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.service.PurchaseService;

import java.util.List;

public class PurchaseServiceImpl implements PurchaseService {
    @Override
    public List<Purchase> showPurchases() {
        PurchaseDaoImpl purchaseDaoImpl = new PurchaseDaoImpl();
        return purchaseDaoImpl.showPurchases();
    }

    @Override
    public void chekout() {
        PurchaseDaoImpl purchaseDaoImpl = new PurchaseDaoImpl();
        purchaseDaoImpl.chekout();

    }

    @Override
    public void makeAPurchase() {
        PurchaseDaoImpl purchaseDaoImpl = new PurchaseDaoImpl();
        purchaseDaoImpl.makeAPurchase();

    }

    @Override
    public List<Good> addIntoPurchase(int id, int amount) {
        PurchaseDaoImpl purchaseDaoImpl = new PurchaseDaoImpl();
        return purchaseDaoImpl.addIntoPurchase(id, amount);

    }

    @Override
    public void deleteFromPurchase(int id) {
        PurchaseDaoImpl purchaseDaoImpl = new PurchaseDaoImpl();
        purchaseDaoImpl.deleteFromPurchase(id);
    }
}
