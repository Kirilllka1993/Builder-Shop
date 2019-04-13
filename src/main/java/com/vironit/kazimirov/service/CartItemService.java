package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.exception.*;

import java.util.List;

public interface CartItemService {

    int addInCartItem(GoodDto goodDto, int amount, PurchaseDto purchaseDto) throws PurchaseException, PurchaseNotFoundException, GoodNotFoundException;

    List<CartItemDto> findCartItems();

    void deleteFromPurchase(CartItemDto cartItemDto) throws PurchaseException, RepeatitionException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException;

    void deleteCartItem(int cartItemId) throws CartItemNotFoundException;

    List<GoodDto> findGoodsByPurchase(int purchaseId) throws PurchaseNotFoundException;

    List<PurchaseDto> findPurchasesByGood(int goodId) throws GoodNotFoundException;

    void deleteCartItemsWithCancelledStatus(PurchaseDto purchaseDto) throws PurchaseNotFoundException;

    void changeAmountInCartItem(int goodId, int amount, int purchaseId) throws PurchaseException, GoodNotFoundException, GoodException;

    CartItemDto findCartItem(int goodId, int purchaseId) throws GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException;

    CartItemDto findCartItemById(int cartItemId) throws CartItemNotFoundException;

    void returnedAmountOfGood(CartItemDto cartItemDto);

    void reduceAmount(int goodId, int amount);

    List<CartItemDto> findCartItemsByPurchase(int purchaseId) throws PurchaseNotFoundException;

    List<CartItemDto> findCartItemsByGood(int goodId) throws GoodNotFoundException;


}
