package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.GoodInPurchase;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface GoodInPurchaseDao {

    void addInGoodInPurchase(Good good, int amount, Purchase purchase) throws RepeatitionException;

    List<GoodInPurchase> findGoodInPurchases();

    void deleteFromPurchase(int goodId) throws PurchaseException;

    void deleteGoodInPurchase();

    List<GoodInPurchase> findByPurchase(int purchaseId);

    List<GoodInPurchase>findByGoods(int goodId);
}
