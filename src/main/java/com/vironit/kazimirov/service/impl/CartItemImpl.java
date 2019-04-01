package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.fakedao.DaoInterface.CartItemDao;
import com.vironit.kazimirov.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemImpl implements CartItemService {
    @Autowired
    private final CartItemDao cartItemDao;
    @Autowired
    private final GoodDao goodDao;

    @Autowired
    public CartItemImpl(CartItemDao cartItemDao, GoodDao goodDao) {
        this.cartItemDao = cartItemDao;
        this.goodDao = goodDao;
    }

    @Override
    @Transactional
    public int addInCartItem(Good good, int amount, Purchase purchase) throws PurchaseException {
        Optional<CartItem> checkGood = Optional.ofNullable(cartItemDao.findCartItem(good.getId(), purchase.getId()));
        if (good.getAmount() < amount || amount < 0) {
            throw new PurchaseException("The amount of good is so much. In the store is present or you entered amount<0 " + " " + good.getAmount());
        } else if (checkGood.isPresent() == true) {
            throw new PurchaseException("This good is present, you can change the amount " + " " + good.getAmount());
        } else {
            cartItemDao.reduceAmount(good.getId(), amount);
            return cartItemDao.addInCartItem(good, amount, purchase);
        }
    }

    @Override
    public List<CartItem> findCartItems() {
        return cartItemDao.findCartItems();
    }

    @Override
    public void deleteFromPurchase(Good good, Purchase purchase) throws PurchaseException {
        CartItem cartItem = cartItemDao.findCartItem(good.getId(), purchase.getId());
        cartItemDao.returnedAmountOfGood(cartItem);
        cartItemDao.deleteFromPurchase(good, purchase);
    }

    @Override
    public void deleteCartItem(int cartItemId) {
        cartItemDao.deleteCartItem(cartItemId);
    }

    @Override
    public List<Good> findGoodsByPurchase(int purchaseId) {
        return cartItemDao.findGoodsByPurchase(purchaseId);
    }


    @Override
    public List<Purchase> findPurchasesByGood(int goodId) {
        return cartItemDao.findPurchasesByGood(goodId);
    }

    @Override
    public void deleteCartItemsWithCancelledStatus(Purchase purchase) {
        cartItemDao.deleteCartItemsWithCancelledStatus(purchase);
    }

    @Override
    public void changeAmountInCartItem(int goodId, int amount, int purchaseId) throws PurchaseException {
        Good good = goodDao.findGoodById(goodId);
        if (good.getAmount() < amount ||amount<0) {
            throw new PurchaseException("The amount of good is so much. In the store is present " + " " + good.getAmount());
        } else {
            cartItemDao.reduceAmount(goodId, amount);
            cartItemDao.changeAmountInCartItem(goodId, amount, purchaseId);
        }
    }

    @Override
    public CartItem findCartItem(int goodId, int purchaseId) {
        return cartItemDao.findCartItem(goodId, purchaseId);
    }

    @Override
    public CartItem findCartItemById(int cartItemId) {
        return cartItemDao.findCartItemById(cartItemId);
    }

    @Override
    public void returnedAmountOfGood(CartItem cartItem) {
        cartItemDao.returnedAmountOfGood(cartItem);
    }

    @Override
    public void reduceAmount(int goodId, int amount) {
        cartItemDao.reduceAmount(goodId, amount);
    }

    @Override
    public List<CartItem> findCartItemsByPurchase(int purchaseId) {
        return cartItemDao.findCartItemsByPurchase(purchaseId);
    }

    @Override
    public List<CartItem> findCartItemsByGood(int goodId) {
        return cartItemDao.findCartItemsByGood(goodId);
    }
}
