package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int addInCartItem(@RequestBody CartItemDto cartItemDto) throws GoodNotFoundException, RepeatitionException, PurchaseException {
        GoodDto goodDto = goodService.findGoodById(cartItemDto.getGoodId());
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        int cartItemId=cartItemService.addInCartItem(goodDto, cartItemDto.getAmount(), purchaseDto);
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
    public void deleteFromCart(@RequestBody CartItemDto cartItemDto) throws GoodNotFoundException, PurchaseException, RepeatitionException {
//        GoodDto goodDto = goodService.findGoodById(cartItemDto.getGoodId());
//        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.deleteFromPurchase(cartItemDto);
    }

    @RequestMapping(value = "admin/cartItem/delete/{cartItemId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartItem(@PathVariable("cartItemId") int cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

    @RequestMapping(value = "admin/cartItem/findGoods", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodDto> findGoodsInPurchase(@RequestBody CartItemDto cartItemDto) {
        List<GoodDto> goodDtos = cartItemService.findGoodsByPurchase(cartItemDto.getPurchaseId());
        return goodDtos;
    }

    @RequestMapping(value = "admin/cartItem/findPurchases", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseDto> findPurchasesByGood(@RequestBody CartItemDto cartItemDto) {
        List<PurchaseDto> purchaseDtos = cartItemService.findPurchasesByGood(cartItemDto.getGoodId());
        return purchaseDtos;
    }

    @RequestMapping(value = "admin/cartItem/deleteWithCancelled", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deletePurchase(@RequestBody PurchaseDto purchaseDto) {
        //Purchase purchase = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.deleteCartItemsWithCancelledStatus(purchaseDto);
    }

    @RequestMapping(value = "cartItem/newAmount", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeAmount(@RequestBody CartItemDto cartItemDto) throws PurchaseException, RepeatitionException {
        cartItemService.changeAmountInCartItem(cartItemDto.getGoodId(), cartItemDto.getAmount(), cartItemDto.getPurchaseId());
    }

    @RequestMapping(value = "admin/cartItem/cartItem", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto findCartItemByGoodAndPurchase(@RequestBody CartItemDto cartItemDto) {
        return cartItemService.findCartItem(cartItemDto.getGoodId(), cartItemDto.getPurchaseId());
    }


    @RequestMapping(value = "admin/cartItem/cartItemById/{cartItemId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto findCartItemById(@PathVariable("cartItemId") int cartItemId) {
        return cartItemService.findCartItemById(cartItemId);
    }

    @RequestMapping(value = "cartItem/oldAmountOfGood/{cartItemId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void returnedAmountOfGood(@PathVariable("cartItemId") int cartItemId) {
        //CartItem cartItem = cartItemService.findCartItemById(cartItemId);
        CartItemDto cartItemDto=cartItemService.findCartItemById(cartItemId);
        cartItemService.returnedAmountOfGood(cartItemDto);
    }

    @RequestMapping(value = "cartItem/newAmountOfGood/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reduceAmount(@RequestBody CartItemDto cartItemDto) {
        cartItemService.reduceAmount(cartItemDto.getGoodId(), cartItemDto.getAmount());
    }

    @RequestMapping(value = "admin/cartItem/allCartByPurchase/{purchaseId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemDto> findCartItemsByPurchase(@PathVariable("purchaseId") int purchaseId) {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItemsByPurchase(purchaseId);
        return cartItemDtos;
    }

    @RequestMapping(value = "admin/cartItem/allCartByGood/{goodId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemDto> findCartItemsByGood(@PathVariable("goodId") int goodId) {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItemsByGood(goodId);
        return cartItemDtos;
    }
}
