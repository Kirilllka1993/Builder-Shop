package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.service.PurchaseService;
import com.vironit.kazimirov.service.impl.PurchaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    //private PurchaseService purchaseService=new PurchaseServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Purchase> purchases=purchaseService.findPurchases();
//        request.setAttribute("purchases",purchases);
//        getServletContext().getRequestDispatcher("/WEB-INF/jsp/purchase.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
