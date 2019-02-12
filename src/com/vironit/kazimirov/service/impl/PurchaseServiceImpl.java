package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.PurchaseDaoImpl;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.service.PurchaseService;

import java.time.LocalDateTime;
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
    public Purchase makeAPurchase(List<Good> goods, Client client, LocalDateTime registration, LocalDateTime purchase, String status) {
        PurchaseDaoImpl purchaseDaoImpl = new PurchaseDaoImpl();
        return purchaseDaoImpl.makeAPurchase(goods, client, registration, purchase, status);

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

    @Override
    public List<Purchase> searchPurchasesByDate(LocalDateTime localDateTime) throws PurchaseNotFoundException {
        PurchaseDaoImpl purchaseDaoImpl = new PurchaseDaoImpl();
        return purchaseDaoImpl.searchPurchasesByDate(localDateTime);
    }
}
