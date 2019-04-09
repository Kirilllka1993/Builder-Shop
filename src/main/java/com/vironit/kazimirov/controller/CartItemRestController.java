package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.exception.*;
import com.vironit.kazimirov.service.CartItemService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartItemRestController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "cartItem/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public int addInCartItem(@RequestBody CartItemDto cartItemDto) throws GoodNotFoundException, RepeatitionException, PurchaseException, PurchaseNotFoundException {
        GoodDto goodDto = goodService.findGoodById(cartItemDto.getGoodId());
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        int cartItemId = cartItemService.addInCartItem(goodDto, cartItemDto.getAmount(), purchaseDto);
        return cartItemId;
    }

    @RequestMapping(value = "admin/cartItem/allCartItems", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemDto> showAllGoodInPurchases() {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        return cartItemDtos;
    }

    @RequestMapping(value = "cartItem/deleteFromCart", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromCart(@RequestBody CartItemDto cartItemDto) throws GoodNotFoundException, PurchaseException, RepeatitionException, PurchaseNotFoundException, CartItemNotFoundException {
        cartItemService.deleteFromPurchase(cartItemDto);
    }

    @RequestMapping(value = "admin/cartItem/delete/{cartItemId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartItem(@PathVariable("cartItemId") int cartItemId) throws CartItemNotFoundException {
        cartItemService.deleteCartItem(cartItemId);
    }

    @RequestMapping(value = "admin/cartItem/findGoods", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodDto> findGoodsInPurchase(@RequestBody CartItemDto cartItemDto) throws PurchaseNotFoundException {
        List<GoodDto> goodDtos = cartItemService.findGoodsByPurchase(cartItemDto.getPurchaseId());
        return goodDtos;
    }

    @RequestMapping(value = "admin/cartItem/findPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseDto> findPurchasesByGood(@RequestBody CartItemDto cartItemDto) throws GoodNotFoundException {
        List<PurchaseDto> purchaseDtos = cartItemService.findPurchasesByGood(cartItemDto.getGoodId());
        return purchaseDtos;
    }

    @RequestMapping(value = "admin/cartItem/deleteWithCancelled", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deletePurchase(@RequestBody PurchaseDto purchaseDto) throws PurchaseNotFoundException {
        cartItemService.deleteCartItemsWithCancelledStatus(purchaseDto);
    }

    @RequestMapping(value = "cartItem/newAmount", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeAmount(@RequestBody CartItemDto cartItemDto) throws PurchaseException, RepeatitionException, GoodNotFoundException, GoodException {
        cartItemService.changeAmountInCartItem(cartItemDto.getGoodId(), cartItemDto.getAmount(), cartItemDto.getPurchaseId());
    }

    @RequestMapping(value = "admin/cartItem/cartItem", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto findCartItemByGoodAndPurchase(@RequestBody CartItemDto cartItemDto) throws GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        return cartItemService.findCartItem(cartItemDto.getGoodId(), cartItemDto.getPurchaseId());
    }


    @RequestMapping(value = "admin/cartItem/cartItemById/{cartItemId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto findCartItemById(@PathVariable("cartItemId") int cartItemId) throws CartItemNotFoundException {
        return cartItemService.findCartItemById(cartItemId);
    }

    @RequestMapping(value = "cartItem/oldAmountOfGood/{cartItemId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void returnedAmountOfGood(@PathVariable("cartItemId") int cartItemId) throws CartItemNotFoundException {
        CartItemDto cartItemDto = cartItemService.findCartItemById(cartItemId);
        cartItemService.returnedAmountOfGood(cartItemDto);
    }

    @RequestMapping(value = "cartItem/newAmountOfGood/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void reduceAmount(@RequestBody CartItemDto cartItemDto) {
        cartItemService.reduceAmount(cartItemDto.getGoodId(), cartItemDto.getAmount());
    }

    @RequestMapping(value = "admin/cartItem/allCartByPurchase/{purchaseId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemDto> findCartItemsByPurchase(@PathVariable("purchaseId") int purchaseId) throws PurchaseNotFoundException {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItemsByPurchase(purchaseId);
        return cartItemDtos;
    }

    @RequestMapping(value = "admin/cartItem/allCartByGood/{goodId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemDto> findCartItemsByGood(@PathVariable("goodId") int goodId) throws GoodNotFoundException {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItemsByGood(goodId);
        return cartItemDtos;
    }
}
