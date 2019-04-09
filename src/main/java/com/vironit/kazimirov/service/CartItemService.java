package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.exception.*;

import java.util.List;

public interface CartItemService {

    int addInCartItem(GoodDto goodDto, int amount, PurchaseDto purchaseDto) throws RepeatitionException, PurchaseException, PurchaseNotFoundException, GoodNotFoundException;//выполнено

    List<CartItemDto> findCartItems();//выполнено

    void deleteFromPurchase(CartItemDto cartItemDto) throws PurchaseException, RepeatitionException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException;//выполнено

    void deleteCartItem(int cartItemId) throws CartItemNotFoundException;//выполнено

    List<GoodDto> findGoodsByPurchase(int purchaseId) throws PurchaseNotFoundException;//выполнено

    List<PurchaseDto> findPurchasesByGood(int goodId) throws GoodNotFoundException;//выполнено

    void deleteCartItemsWithCancelledStatus(PurchaseDto purchaseDto) throws PurchaseNotFoundException;//выполнено

    void changeAmountInCartItem(int goodId, int amount, int purchaseId) throws PurchaseException, RepeatitionException, GoodNotFoundException, GoodException;//выполнено

    CartItemDto findCartItem(int goodId, int purchaseId) throws GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException;//выполнено

    CartItemDto findCartItemById(int cartItemId) throws CartItemNotFoundException;//выполнено

    void returnedAmountOfGood(CartItemDto cartItemDto);//

    void reduceAmount(int goodId, int amount);//

    List<CartItemDto> findCartItemsByPurchase(int purchaseId) throws PurchaseNotFoundException;//админ

    List<CartItemDto> findCartItemsByGood(int goodId) throws GoodNotFoundException;//админ


}
