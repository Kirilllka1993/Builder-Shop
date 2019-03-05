package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.fakedao.PurchaseDaoImpl;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.vironit.kazimirov.entity.Status.CANCELED;
@Component
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseDao purchaseDao;


    public PurchaseServiceImpl() {
        purchaseDao = new PurchaseDaoImpl();
    }

    @Override
    public List<Purchase> findPurchases() {
        return purchaseDao.findPurchases();
    }

    @Override
    public Purchase createNewPurchase(Client client) {
        return purchaseDao.createNewPurchase(client);
    }


    @Override
    public Purchase makeAPurchase(Purchase purchase) throws PurchaseException {
        //System.out.println(purchase.getStatus());
        if (purchase.getStatus()==CANCELED){
            throw new PurchaseException("The purchase is canceled");
        }
        return purchaseDao.makeAPurchase(purchase);

    }

    @Override
    public Purchase addIntoPurchase(int id, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException {
        return purchaseDao.addIntoPurchase(id, amount, purchase);

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

    @Override
    public Purchase changeStatus(Purchase purchase, Status status) {
        return purchaseDao.changeStatus(purchase,status);
    }

    @Override
    public void removePurchase(int id) throws PurchaseException {
        purchaseDao.removePurchase(id);
    }
}
