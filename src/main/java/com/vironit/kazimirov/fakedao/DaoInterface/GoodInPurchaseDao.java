package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.GoodInPurchase;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface GoodInPurchaseDao {

    void addInGoodInPurchase(Good good, int amount, Purchase purchase) throws PurchaseException;

    List<GoodInPurchase> findGoodInPurchases();

    void deleteFromPurchase(Good good, Purchase purchase) throws PurchaseException;

    void deleteGoodInPurchase(int goodInPurchase);

    List<Good> findGoodsByPurchase(int purchaseId);

    List<Purchase> findPurchasesByGood(int goodId);

    void deleteGoodInPurchasesWithCancelledStatus(Purchase purchase);

    void changeAmountInGoodInPurchase (int goodId, int amount, int purchaseId);

    GoodInPurchase findGoodInPurchase (int goodId, int purchaseId);

    GoodInPurchase findGoodInPurchaseById(int goodInPurchaseId);

    void returnedAmountOfGood(GoodInPurchase goodInPurchase);

    void reduceAmount(int goodId, int amount);

    List<GoodInPurchase> findGoodInPurchasesByPurchase(int purchaseId);

    List<GoodInPurchase> findGoodInPurchasesByGood(int goodId);
}
