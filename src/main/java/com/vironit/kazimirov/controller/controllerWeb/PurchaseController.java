package com.vironit.kazimirov.controller.controllerWeb;

import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.CartItemService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import java.util.List;

@Controller
public class PurchaseController extends HttpServlet {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private CartItemService cartItemService;

    @RequestMapping(value = "/purchasePage", method = RequestMethod.GET)
    public ModelAndView moveToGoodPage(ModelMap map) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/createPurchase", method = RequestMethod.POST)
    public ModelAndView createPurchase(@RequestParam("clientLogin") String clientLogin) throws ClientNotFoundException {
        User user = adminService.findClientByLogin(clientLogin);
        purchaseService.createNewPurchase(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

//    @RequestMapping(value = "/addIntoPurchase", method = RequestMethod.POST)
//    public ModelAndView addIntoPurchase(@RequestParam("goodId") int goodId,
//                                        @RequestParam("amount") int amount,
//                                        @RequestParam("purchaseId") int purchaseId) throws GoodNotFoundException {
//        Purchase purchase = purchaseService.findPurchaseById(purchaseId);
//        Good good = goodService.findGoodById(goodId);
//        ModelAndView modelAndView = new ModelAndView();
//        try {
//            purchaseService.addIntoPurchase(good, amount, purchase);
//            modelAndView.setViewName("purchase");
//        } catch (RepeatitionException e) {
//            e.printStackTrace();
//            //modelAndView.setViewName("purchase");
//            modelAndView.setViewName("tryLogin");
//            return modelAndView;
//        }
//        return modelAndView;
//    }

//    @RequestMapping(value = "/findPurchaseById", method = RequestMethod.GET)
//    public ModelAndView findPurchaseById(@RequestParam("purchaseId")int purchaseId) throws RepeatitionException, GoodNotFoundException {
//        Purchase purchase=purchaseService.findPurchaseById(purchaseId);
//        Good good=goodService.findGoodById(goodId);
//        purchaseService.addIntoPurchase(good,amount,purchase);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("purchase");
//        return modelAndView;
//    }

    @RequestMapping(value = "/showPurchases", method = RequestMethod.GET)
    public ModelAndView findPurchases(ModelMap map){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("purchase");
        //List<Purchase> purchases = purchaseService.findPurchases();
        List<CartItem> purchases= cartItemService.findCartItems();
        map.addAttribute("purchases", purchases);
        return modelAndView;
    }

    @RequestMapping(value = "/makeAPurchase", method = RequestMethod.POST)
    public ModelAndView addIntoPurchase(@RequestParam("purchaseId") int purchaseId) throws GoodNotFoundException, PurchaseException {
        //Purchase purchase = purchaseService.findPurchaseById(purchaseId);
        ModelAndView modelAndView = new ModelAndView();
            purchaseService.makeAPurchase(purchaseId);
            modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/changeAmount", method = RequestMethod.POST)
    public ModelAndView changeAmount(@RequestParam("goodId") int goodId,
                                     @RequestParam("amount") int amount,
                                     @RequestParam("purchaseId") int purchaseId) throws GoodNotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        try {
            cartItemService.changeAmountInCartItem(goodId,amount,purchaseId);
            modelAndView.setViewName("purchase");
        } catch (PurchaseException e) {
            e.printStackTrace();
            modelAndView.setViewName("tryLogin");
            return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("goodId") int goodId,
                                     @RequestParam("purchaseId") int purchaseId) throws GoodNotFoundException, PurchaseException {
        Purchase purchase = purchaseService.findPurchaseById(purchaseId);
        Good good = goodService.findGoodById(goodId);
        ModelAndView modelAndView = new ModelAndView();
            cartItemService.deleteFromPurchase(good,purchase);
            modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/deleteCancelled", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("purchaseId") int purchaseId) throws GoodNotFoundException, PurchaseException {
        Purchase purchase = purchaseService.findPurchaseById(purchaseId);
        ModelAndView modelAndView = new ModelAndView();
        cartItemService.deleteCartItemsWithCancelledStatus(purchase);
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/findGoods", method = RequestMethod.GET)
    public ModelAndView findGoods(@RequestParam("purchaseId") int purchaseId,
            @RequestParam("goodId")int goodId
            ,ModelMap map) throws GoodNotFoundException, PurchaseException {
        ModelAndView modelAndView = new ModelAndView();
        List<Good>goods= cartItemService.findGoodsByPurchase(purchaseId);
        List<Purchase> purchases= cartItemService.findPurchasesByGood(goodId);
        map.addAttribute("goods",goods);
        map.addAttribute("purchases",purchases);
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/findGoodInPurchase", method = RequestMethod.GET)
    public ModelAndView findGoods(@RequestParam("goodInPurchaseId") int goodInPurchaseId
                                              ,ModelMap map) throws GoodNotFoundException, PurchaseException {
        ModelAndView modelAndView = new ModelAndView();
        CartItem cartItem = cartItemService.findCartItemById(goodInPurchaseId);
        map.addAttribute("goodInPurchase", cartItem);
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/findGoodInPurchaseByParametres", method = RequestMethod.GET)
    public ModelAndView findGoodsByParametres(@RequestParam("goodId") int goodId,
            @RequestParam("purchaseId") int purchaseId,ModelMap map) throws GoodNotFoundException, PurchaseException {
        ModelAndView modelAndView = new ModelAndView();
        CartItem cartItem = cartItemService.findCartItem(goodId,purchaseId);
        map.addAttribute("goodInPurchase", cartItem);
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @RequestMapping(value = "/deletePurchaseWithCancelled", method = RequestMethod.GET)
    public ModelAndView findGoodsByParametres(@RequestParam("purchaseId") int purchaseId,ModelMap map) throws GoodNotFoundException, PurchaseException {
        ModelAndView modelAndView = new ModelAndView();
        Purchase purchase=purchaseService.findPurchaseById(purchaseId);
        cartItemService.deleteCartItemsWithCancelledStatus(purchase);
        modelAndView.setViewName("purchase");
        return modelAndView;
    }
}
