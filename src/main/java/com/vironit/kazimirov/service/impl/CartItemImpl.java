package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.*;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.fakedao.DaoInterface.CartItemDao;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.service.CartItemService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartItemImpl implements CartItemService {
    @Autowired
    private final CartItemDao cartItemDao;
    @Autowired
    private final GoodDao goodDao;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private GoodService goodService;

    @Autowired
    public CartItemImpl(CartItemDao cartItemDao, GoodDao goodDao) {
        this.cartItemDao = cartItemDao;
        this.goodDao = goodDao;
    }

    @Override
    public int addInCartItem(GoodDto goodDto, int amount, PurchaseDto purchaseDto) throws PurchaseException,
            PurchaseNotFoundException, GoodNotFoundException {
        Optional<CartItem> checkGood = Optional.ofNullable(cartItemDao.findCartItem(goodDto.getId(), purchaseDto.getId()));
        purchaseService.findPurchaseById(purchaseDto.getId());
        goodService.findGoodById(goodDto.getId());
        if (goodDto.getAmount() < amount || amount < 0) {
            throw new PurchaseException("The amount of goodId is so much. In the store is present or you entered amount<0 " + " " + goodDto.getAmount());
        } else if (checkGood.isPresent() == true) {
            throw new PurchaseException("This goodId is present, you can change only the amount " + " " + goodDto.getAmount());
        } else if (!purchaseDto.getStatus().equals("NEW")) {
            throw new PurchaseException("this purchase can't using due to status not NEW");
        } else {
            cartItemDao.reduceAmount(goodDto.getId(), amount);
            Good good = goodDao.findGoodById(goodDto.getId());
            Purchase purchase = purchaseDao.findPurchaseById(purchaseDto.getId());
            return cartItemDao.addInCartItem(good, amount, purchase);
        }
    }


    @Override
    public List<CartItemDto> findCartItems() {
        List<CartItem> cartItems = cartItemDao.findCartItems();
        List<CartItemDto> cartItemDtos = cartItems.stream().map(CartItemDto::new).collect(Collectors.toList());
        return cartItemDtos;
    }

    @Override
    public void deleteFromPurchase(CartItemDto cartItemDto) throws PurchaseException, GoodNotFoundException, PurchaseNotFoundException {
        Optional<Good> checkNameGood = Optional.ofNullable(goodDao.findGoodById(cartItemDto.getGoodId()));
        purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        if (checkNameGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            Good good = goodDao.findGoodById(cartItemDto.getGoodId());
            Purchase purchase = purchaseDao.findPurchaseById(cartItemDto.getPurchaseId());
            CartItem cartItem = cartItemDao.findCartItem(good.getId(), purchase.getId());
            cartItemDao.returnedAmountOfGood(cartItem);
            cartItemDao.deleteFromPurchase(good, purchase);
        }
    }

    @Override
    public void deleteCartItem(int cartItemId) throws CartItemNotFoundException {
        Optional<CartItem> checkCarItemName = Optional.ofNullable(cartItemDao.findCartItemById(cartItemId));
        if (checkCarItemName.isPresent() == false) {
            throw new CartItemNotFoundException("such cartItem is absent");
        } else {
            CartItem cartItem = cartItemDao.findCartItemById(cartItemId);
            cartItemDao.returnedAmountOfGood(cartItem);
            cartItemDao.deleteCartItem(cartItemId);
        }
    }

    @Override
    public List<GoodDto> findGoodsByPurchase(int purchaseId) throws PurchaseNotFoundException {
        purchaseService.findPurchaseById(purchaseId);
        List<Good> goods = cartItemDao.findGoodsByPurchase(purchaseId);
        List<GoodDto> goodDtos = goods.stream().map(GoodDto::new).collect(Collectors.toList());
        return goodDtos;
    }


    @Override
    public List<PurchaseDto> findPurchasesByGood(int goodId) throws GoodNotFoundException {
        goodService.findGoodById(goodId);
        List<Purchase> purchases = cartItemDao.findPurchasesByGood(goodId);
        List<PurchaseDto> purchaseDtos = purchases.stream().map(PurchaseDto::new).collect(Collectors.toList());
        return purchaseDtos;
    }

    @Override
    public void deleteCartItemsWithCancelledStatus(PurchaseDto purchaseDto) throws PurchaseNotFoundException {
        purchaseService.findPurchaseById(purchaseDto.getId());
        Purchase purchase = purchaseDao.findPurchaseById(purchaseDto.getId());
        cartItemDao.deleteCartItemsWithCancelledStatus(purchase);
    }

    @Override
    public void changeAmountInCartItem(int goodId, int amount, int purchaseId) throws PurchaseException,GoodNotFoundException, GoodException {
        goodService.findGoodById(goodId);
        Good good = goodDao.findGoodById(goodId);
        CartItem cartItem = cartItemDao.findCartItem(goodId, purchaseId);
        int oldAmount = cartItem.getAmount();
        if (good.getAmount() + oldAmount < amount || amount < 0) {
            throw new PurchaseException("The amount of goodId is so much. In the store is present " + " " + good.getAmount());
        } else {
            cartItemDao.changeAmountInCartItem(goodId, amount, purchaseId);
            goodService.changeAmount(goodId, good.getAmount() + oldAmount - amount);
        }
    }

    @Override
    public CartItemDto findCartItem(int goodId, int purchaseId) throws GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        Optional<CartItem> checkCarItemName = Optional.ofNullable(cartItemDao.findCartItem(goodId, purchaseId));
        goodService.findGoodById(goodId);
        purchaseService.findPurchaseById(purchaseId);
        if (checkCarItemName.isPresent() == false) {
            throw new CartItemNotFoundException("such cartItem is absent");
        }
        CartItem cartItem = cartItemDao.findCartItem(goodId, purchaseId);
        CartItemDto cartItemDto = new CartItemDto(cartItem);
        return cartItemDto;
    }

    @Override
    public CartItemDto findCartItemById(int cartItemId) throws CartItemNotFoundException {
        Optional<CartItem> checkCarItemName = Optional.ofNullable(cartItemDao.findCartItemById(cartItemId));
        if (checkCarItemName.isPresent() == false) {
            throw new CartItemNotFoundException("such cartItem is absent");
        } else {
            CartItem cartItem = cartItemDao.findCartItemById(cartItemId);
            CartItemDto cartItemDto = new CartItemDto(cartItem);
            return cartItemDto;
        }
    }

    @Override
    public void returnedAmountOfGood(CartItemDto cartItemDto) {
        CartItem cartItem = cartItemDao.findCartItemById(cartItemDto.getId());
        cartItemDao.returnedAmountOfGood(cartItem);
    }

    @Override
    public void reduceAmount(int goodId, int amount) {
        cartItemDao.reduceAmount(goodId, amount);
    }

    @Override
    public List<CartItemDto> findCartItemsByPurchase(int purchaseId) throws PurchaseNotFoundException {
        purchaseService.findPurchaseById(purchaseId);
        List<CartItem> cartItems = cartItemDao.findCartItemsByPurchase(purchaseId);
        List<CartItemDto> cartItemDtos = cartItems.stream().map(CartItemDto::new).collect(Collectors.toList());
        return cartItemDtos;
    }

    @Override
    public List<CartItemDto> findCartItemsByGood(int goodId) throws GoodNotFoundException {
        goodService.findGoodById(goodId);
        List<CartItem> cartItems = cartItemDao.findCartItemsByGood(goodId);
        List<CartItemDto> cartItemDtos = cartItems.stream().map(CartItemDto::new).collect(Collectors.toList());
        return cartItemDtos;
    }
}
