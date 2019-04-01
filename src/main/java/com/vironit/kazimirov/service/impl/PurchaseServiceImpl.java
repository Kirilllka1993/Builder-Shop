package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.vironit.kazimirov.entity.Status.CANCELED;
@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private final PurchaseDao purchaseDao;
    @Autowired
    private final GoodDao goodDao;


    @Autowired
    public PurchaseServiceImpl(PurchaseDao purchaseDao,GoodDao goodDao) {
        this.purchaseDao = purchaseDao;
        this.goodDao = goodDao;
    }

    @Override
    public List<Purchase> findPurchases() {
        return purchaseDao.findPurchases();
    }

    @Override
    public int createNewPurchase(Client client) {
        return purchaseDao.createNewPurchase(client);
    }

    @Override
    public Purchase findPurchaseById(int purchaseId) {
        return purchaseDao.findPurchaseById(purchaseId);
    }


    @Override
    public void makeAPurchase(int purchaseId) throws PurchaseException {
        //System.out.println(purchase.getStatus());
        Purchase purchase=purchaseDao.findPurchaseById(purchaseId);
        if (purchase.getStatus().equals(CANCELED)) {
            throw new PurchaseException("The purchase is canceled");
        }
        purchaseDao.makeAPurchase(purchaseId);

    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime timeOfPurchase) {
        return purchaseDao.findPurchasesByDate(timeOfPurchase);
    }

    @Override
    public void changeStatus(Purchase purchase, Status status) {
         purchaseDao.changeStatus(purchase,status);
    }

    @Override
    public void removePurchase(int purchaseId) {
        purchaseDao.removePurchase(purchaseId);
    }
}
