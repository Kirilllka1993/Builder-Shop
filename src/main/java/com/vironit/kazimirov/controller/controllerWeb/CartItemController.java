package com.vironit.kazimirov.controller.controllerWeb;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.CartItemService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/addIntoGoodInPurchase", method = RequestMethod.POST)
    public ModelAndView addIntoPurchase(@RequestParam("goodId") int goodId,
                                        @RequestParam("amount") int amount,
                                        @RequestParam("purchaseId") int purchaseId) throws GoodNotFoundException, RepeatitionException, PurchaseException {
        Purchase purchase = purchaseService.findPurchaseById(purchaseId);
        Good good = goodService.findGoodById(goodId);
        ModelAndView modelAndView = new ModelAndView();
            cartItemService.addInCartItem(good, amount, purchase);
            modelAndView.setViewName("purchase");
        return modelAndView;
    }

}
