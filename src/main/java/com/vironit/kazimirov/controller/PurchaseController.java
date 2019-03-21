package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import com.vironit.kazimirov.service.impl.PurchaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
//@WebServlet("/purchaseController")
public class PurchaseController extends HttpServlet {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/purchasePage", method = RequestMethod.GET)
    public ModelAndView moveToGoodPage(ModelMap map) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/createPurchase", method = RequestMethod.POST)
    public ModelAndView createPurchase(@RequestParam ("clientLogin") String clientLogin) throws ClientNotFoundException {
        Client client = adminService.findClientByLogin(clientLogin);
        purchaseService.createNewPurchase(client);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/addIntoPurchase", method = RequestMethod.POST)
    public ModelAndView addIntoPurchase(@RequestParam ("goodId") int goodId,
                                        @RequestParam ("amount") int amount,
                                        @RequestParam("purchaseId")int purchaseId) throws RepeatitionException, GoodNotFountException {
        Purchase purchase=purchaseService.findPurchaseById(purchaseId);
        Good good=goodService.findGoodById(goodId);
        purchaseService.addIntoPurchase(good,amount,purchase);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

//    @RequestMapping(value = "/findPurchaseById", method = RequestMethod.GET)
//    public ModelAndView findPurchaseById(@RequestParam("purchaseId")int purchaseId) throws RepeatitionException, GoodNotFountException {
//        Purchase purchase=purchaseService.findPurchaseById(purchaseId);
//        Good good=goodService.findGoodById(goodId);
//        purchaseService.addIntoPurchase(good,amount,purchase);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("purchase");
//        return modelAndView;
//    }
}
