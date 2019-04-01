package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;

import java.util.List;

public interface CartItemDao {

    int addInCartItem(Good good, int amount, Purchase purchase) throws PurchaseException;

    List<CartItem> findCartItems();

    void deleteFromPurchase(Good good, Purchase purchase) throws PurchaseException;

    void deleteCartItem(int cartItemId);

    List<Good> findGoodsByPurchase(int purchaseId);

    List<Purchase> findPurchasesByGood(int goodId);

    void deleteCartItemsWithCancelledStatus(Purchase purchase);

    void changeAmountInCartItem(int goodId, int amount, int purchaseId);

    CartItem findCartItem(int goodId, int purchaseId);

    CartItem findCartItemById(int cartItemId);

    void returnedAmountOfGood(CartItem cartItem);

    void reduceAmount(int goodId, int amount);

    List<CartItem> findCartItemsByPurchase(int purchaseId);

    List<CartItem> findCartItemsByGood(int goodId);
}
