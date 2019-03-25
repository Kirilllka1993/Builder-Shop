package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.GoodInPurchase;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodInPurchaseDao;
import com.vironit.kazimirov.service.GoodInPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodInPurchaseImpl implements GoodInPurchaseService {
    @Autowired
    private final GoodInPurchaseDao goodInPurchaseDao;
    @Autowired
    private final GoodDao goodDao;

    public GoodInPurchaseImpl(GoodInPurchaseDao goodInPurchaseDao, GoodDao goodDao) {
        this.goodInPurchaseDao = goodInPurchaseDao;
        this.goodDao = goodDao;
    }

    @Override
    public void addInGoodInPurchase(Good good, int amount, Purchase purchase) throws PurchaseException {
        Optional<GoodInPurchase> checkGood = Optional.ofNullable(goodInPurchaseDao.findGoodInPurchase(good.getId(), purchase.getId()));
        if (good.getAmount() < amount || amount < 0) {
            throw new PurchaseException("The amount of good is so much. In the store is present or you entered amount<0 " + " " + good.getAmount());
        } else if (checkGood.isPresent() == true) {
            throw new PurchaseException("This good is present, you can change the amount " + " " + good.getAmount());
        } else {
            goodInPurchaseDao.reduceAmount(good.getId(), amount);
            goodInPurchaseDao.addInGoodInPurchase(good, amount, purchase);
        }


    }

    @Override
    public List<GoodInPurchase> findGoodInPurchases() {
        return goodInPurchaseDao.findGoodInPurchases();
    }

    @Override
    public void deleteFromPurchase(Good good, Purchase purchase) throws PurchaseException {
        GoodInPurchase goodInPurchase = goodInPurchaseDao.findGoodInPurchase(good.getId(), purchase.getId());
        int amount=goodInPurchase.getAmount();
        goodInPurchaseDao.returnedAmountOfGood(goodInPurchase);
        goodInPurchaseDao.deleteFromPurchase(good, purchase);
    }

    @Override
    public void deleteGoodInPurchase(int goodInPurchase) {
        goodInPurchaseDao.deleteGoodInPurchase(goodInPurchase);
    }

    @Override
    public List<Good> findGoodsByPurchase(int purchaseId) {
        return goodInPurchaseDao.findGoodsByPurchase(purchaseId);
    }


    @Override
    public List<Purchase> findPurchasesByGood(int goodId) {
        return goodInPurchaseDao.findPurchasesByGood(goodId);
    }

    @Override
    public void deleteGoodInPurchasesWithCancelledStatus(Purchase purchase) {
        goodInPurchaseDao.deleteGoodInPurchasesWithCancelledStatus(purchase);
    }

    @Override
    public void changeAmountInGoodInPurchase(int goodId, int amount, int purchaseId) throws RepeatitionException {
        Good good = goodDao.findGoodById(goodId);
        if (good.getAmount() < amount) {
            throw new RepeatitionException("The amount of good is so much. In the store is present " + " " + good.getAmount());
        } else {
            goodInPurchaseDao.reduceAmount(goodId, amount);
            goodInPurchaseDao.changeAmountInGoodInPurchase(goodId, amount, purchaseId);
        }
    }

    @Override
    public GoodInPurchase findGoodInPurchase(int goodId, int purchaseId) {
        return goodInPurchaseDao.findGoodInPurchase(goodId, purchaseId);
    }

    @Override
    public GoodInPurchase findGoodInPurchaseById(int goodInPurchaseId) {
        return goodInPurchaseDao.findGoodInPurchaseById(goodInPurchaseId);
    }

    @Override
    public void returnedAmountOfGood(GoodInPurchase goodInPurchase) {
        goodInPurchaseDao.returnedAmountOfGood(goodInPurchase);
    }

    @Override
    public void reduceAmount(int goodId, int amount) {
        goodInPurchaseDao.reduceAmount(goodId, amount);
    }
}
