package com.vironit.kazimirov.dao.impl;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public class PurchaseDaoImpl implements PurchaseDao {
    @Override
    public List<Purchase> findPurchases() {
        return null;
    }

    @Override
    public Purchase createNewPurchase(Client client) {
        return null;
    }

    @Override
    public Purchase makeAPurchase(Purchase purchase) throws PurchaseException {
        return null;
    }

    @Override
    public Purchase addIntoPurchase(int id, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException {
        return null;
    }

    @Override
    public void deleteFromPurchase(int id) throws PurchaseException {

    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime localDateTime) throws PurchaseNotFoundException {
        return null;
    }

    @Override
    public List<Good> findGoods() {
        return null;
    }

    @Override
    public void removePurchase(int id) throws PurchaseException {

    }

    @Override
    public Purchase changeStatus(Purchase purchase, Status status) {
        return null;
    }
}
