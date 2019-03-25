package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
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


    public PurchaseServiceImpl(PurchaseDao purchaseDao,GoodDao goodDao) {
        this.purchaseDao = purchaseDao;
        this.goodDao = goodDao;
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
    public void addIntoPurchase(Good good, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException {
        if (good.getAmount()<amount){
            throw new RepeatitionException("The amount of good is so much. In the store is present "+" "+good.getAmount() );
        }else{
            //goodDao.changeAmountOfGood(good,amount);
            purchaseDao.addIntoPurchase(good, amount, purchase);
            //goodDao.changeAmountOfGood(good,amount);
        }
    }

    @Override
    public void deleteFromPurchase(int goodId) throws PurchaseException {
        purchaseDao.deleteFromPurchase(goodId);
    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime registration) throws PurchaseNotFoundException {
        return purchaseDao.findPurchasesByDate(registration);
    }

//    @Override
//    public List<Good> findGoods() {
//        return purchaseDao.findGoods();
//    }

    @Override
    public void changeStatus(Purchase purchase, Status status) {
         purchaseDao.changeStatus(purchase,status);
    }

    @Override
    public void removePurchase(int purchaseId) throws PurchaseException {
        purchaseDao.removePurchase(purchaseId);
    }
}
