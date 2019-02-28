package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.service.PurchaseService;
import com.vironit.kazimirov.service.impl.PurchaseServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/purchaseController")
public class PurchaseController extends HttpServlet {
    private PurchaseService purchaseService=new PurchaseServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Purchase> purchases=purchaseService.findPurchases();
        request.setAttribute("purchases",purchases);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/purchase.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
