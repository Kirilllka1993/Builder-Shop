package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface CartItemService {

    int addInCartItem(Good good, int amount, Purchase purchase) throws RepeatitionException, PurchaseException;//выполнено

    List<CartItem> findCartItems();//выполнено

    void deleteFromPurchase(Good good, Purchase purchase) throws PurchaseException;//выполнено

    void deleteCartItem(int cartItemId);//выполнено

    List<Good> findGoodsByPurchase(int purchaseId);//выполнено

    List<Purchase> findPurchasesByGood(int goodId);//выполнено

    void deleteCartItemsWithCancelledStatus(Purchase purchase);//выполнено

    void changeAmountInCartItem(int goodId, int amount, int purchaseId) throws PurchaseException;//выполнено

    CartItem findCartItem(int goodId, int purchaseId);//выполнено

    CartItem findCartItemById(int cartItemId);//выполнено

    void returnedAmountOfGood(CartItem cartItem);//выполнено

    void reduceAmount(int goodId, int amount);//выполнено

    List<CartItem> findCartItemsByPurchase(int purchaseId);//выполнено

    List<CartItem> findCartItemsByGood(int goodId);//выполнено


}
