package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.dao.PurchaseDaoImpl;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurchaseService;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseServiceImpl implements PurchaseService {

    private PurchaseDao purchaseDao;

    public PurchaseServiceImpl() {
        purchaseDao = new PurchaseDaoImpl();
    }

    @Override
    public List<Purchase> findPurchases() {
        return purchaseDao.findPurchases();
    }

    @Override
    public void chekout() {
        purchaseDao.chekout();

    }

    @Override
    public Purchase makeAPurchase(Purchase purchase) throws PurchaseException {
        return purchaseDao.makeAPurchase(purchase);

    }

    @Override
    public List<Good> addIntoPurchase(int id, int amount) throws RepeatitionException {
        return purchaseDao.addIntoPurchase(id, amount);

    }

    @Override
    public void deleteFromPurchase(int id) throws PurchaseException {
        purchaseDao.deleteFromPurchase(id);
    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime localDateTime) throws PurchaseNotFoundException {
        return purchaseDao.findPurchasesByDate(localDateTime);
    }

    @Override
    public List<Good> findGoods() {
        return purchaseDao.findGoods();
    }
}
