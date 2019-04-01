package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.CartItemService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "cartItem")
public class CartItemRestController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int addInCartItem(@RequestBody CartItemDto cartItemDto) throws GoodNotFoundException, RepeatitionException, PurchaseException {
        Good good = goodService.findGoodById(cartItemDto.getGoodId());
        Purchase purchase = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        int cartItemId=cartItemService.addInCartItem(good, cartItemDto.getAmount(), purchase);
        return cartItemId;
    }

    @RequestMapping(value = "/allCartItems", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItem> showAllGoodInPurchases() {
        List<CartItem> cartItems = cartItemService.findCartItems();
        return cartItems;
    }

    @RequestMapping(value = "/deleteFromCart", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFromCart(@RequestBody CartItemDto cartItemDto) throws GoodNotFoundException, PurchaseException {
        Good good = goodService.findGoodById(cartItemDto.getGoodId());
        Purchase purchase = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.deleteFromPurchase(good, purchase);
    }

    @RequestMapping(value = "delete/{cartItemId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable("cartItemId") int cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

    @RequestMapping(value = "/findGoods", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Good> findGoodsInPurchase(@RequestBody CartItemDto cartItemDto) {
        List<Good> goods = cartItemService.findGoodsByPurchase(cartItemDto.getPurchaseId());
        return goods;
    }

    @RequestMapping(value = "/findPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Purchase> findPurchasesByGood(@RequestBody CartItemDto cartItemDto) {
        List<Purchase> purchases = cartItemService.findPurchasesByGood(cartItemDto.getGoodId());
        return purchases;
    }

    @RequestMapping(value = "/deleteWithCancelled", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePurchase(@RequestBody CartItemDto cartItemDto) {
        Purchase purchase = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.deleteCartItemsWithCancelledStatus(purchase);
    }

    @RequestMapping(value = "/newAmount", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAmount(@RequestBody CartItemDto cartItemDto) throws PurchaseException {
        cartItemService.changeAmountInCartItem(cartItemDto.getGoodId(), cartItemDto.getAmount(), cartItemDto.getPurchaseId());
    }

    @RequestMapping(value = "/cartItem", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CartItem findCartItemByGoodAndPurchase(@RequestBody CartItemDto cartItemDto) {
        return cartItemService.findCartItem(cartItemDto.getGoodId(), cartItemDto.getPurchaseId());
    }


    @RequestMapping(value = "/cartItemById/{cartItemId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CartItem findCartItemById(@PathVariable("cartItemId") int cartItemId) {
        return cartItemService.findCartItemById(cartItemId);
    }

    @RequestMapping(value = "/oldAmountOfGood/{cartItemId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnedAmountOfGood(@PathVariable("cartItemId") int cartItemId) {
        CartItem cartItem = cartItemService.findCartItemById(cartItemId);
        cartItemService.returnedAmountOfGood(cartItem);
    }

    @RequestMapping(value = "/newAmountOfGood/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reduceAmount(@RequestBody CartItemDto cartItemDto) {
        cartItemService.reduceAmount(cartItemDto.getGoodId(), cartItemDto.getAmount());
    }

    @RequestMapping(value = "/allCartByPurchase/{purchaseId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItem> findCartItemsByPurchase(@PathVariable("purchaseId") int purchaseId) {
        List<CartItem> cartItems = cartItemService.findCartItemsByPurchase(purchaseId);
        return cartItems;
    }

    @RequestMapping(value = "/allCartByGood/{goodId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItem> findCartItemsByGood(@PathVariable("goodId") int goodId) {
        List<CartItem> cartItems = cartItemService.findCartItemsByGood(goodId);
        return cartItems;
    }
}
