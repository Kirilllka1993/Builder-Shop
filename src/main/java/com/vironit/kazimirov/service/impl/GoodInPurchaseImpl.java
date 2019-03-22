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
    public void addInGoodInPurchase(Good good, int amount, Purchase purchase) throws RepeatitionException {
        if (good.getAmount()<amount){
            throw new RepeatitionException("The amount of good is so much. In the store is present "+" "+good.getAmount() );
        }else{
            goodDao.changeAmountOfGood(good,amount);
            good.setAmount(amount);//???????????
            goodInPurchaseDao.addInGoodInPurchase(good,amount,purchase);

        }


    }

    @Override
    public List<GoodInPurchase> findGoodInPurchases() {
        return goodInPurchaseDao.findGoodInPurchases();
    }

    @Override
    public void deleteFromPurchase(int goodId) throws PurchaseException {

    }

    @Override
    public void deleteGoodInPurchase() {

    }

    @Override
    public List<GoodInPurchase> findByPurchase(int purchaseId) {
        return null;
    }

    @Override
    public List<GoodInPurchase> findByGoods(int goodId) {
        return null;
    }
}
