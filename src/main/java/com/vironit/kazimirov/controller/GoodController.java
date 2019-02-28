package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.impl.GoodServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/goodController")
public class GoodController extends HttpServlet {
    private GoodService goodService=new GoodServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Good> goods=goodService.findAllGoods();
        request.setAttribute("goods",goods);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/good.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
