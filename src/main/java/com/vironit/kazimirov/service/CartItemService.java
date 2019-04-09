package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface CartItemService {

    int addInCartItem(GoodDto goodDto, int amount, PurchaseDto purchaseDto) throws RepeatitionException, PurchaseException;//пользователь

    List<CartItemDto> findCartItems();//админ

    void deleteFromPurchase(CartItemDto cartItemDto) throws PurchaseException, RepeatitionException;//пользователь

    void deleteCartItem(int cartItemId);//админ

    List<GoodDto> findGoodsByPurchase(int purchaseId);//админ

    List<PurchaseDto> findPurchasesByGood(int goodId);//админ

    void deleteCartItemsWithCancelledStatus(PurchaseDto purchaseDto);//?

    void changeAmountInCartItem(int goodId, int amount, int purchaseId) throws PurchaseException, RepeatitionException;//пользователь

    CartItemDto findCartItem(int goodId, int purchaseId);//админ

    CartItemDto findCartItemById(int cartItemId);//админ

    void returnedAmountOfGood(CartItemDto cartItemDto);//выполнено

    void reduceAmount(int goodId, int amount);//выполнено

    List<CartItemDto> findCartItemsByPurchase(int purchaseId);//админ

    List<CartItemDto> findCartItemsByGood(int goodId);//админ


}
