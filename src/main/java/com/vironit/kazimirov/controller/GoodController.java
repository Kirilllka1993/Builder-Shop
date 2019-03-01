package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.daoJdbc.AdminDaoImplJdbs;
import com.vironit.kazimirov.daoJdbc.GoodDaoImplJdbc;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.impl.GoodServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/goodController")
public class GoodController extends HttpServlet {
    private GoodService goodService = new GoodServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Good> goods=goodService.findAllGoods();
//        request.setAttribute("goods",goods);
       getServletContext().getRequestDispatcher("/WEB-INF/jsp/good.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String URL = "jdbc:mysql://localhost:3306/shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "1111";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("SqlException ");
        }
        GoodDaoImplJdbc goodDaoImplJdbc = new GoodDaoImplJdbc(connection);
        List<Subsection> subsections=new ArrayList<>();

        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            subsections=goodDaoImplJdbc.showGoods();
            //PrintWriter printWriter=resp.getWriter();
            req.setAttribute("subsections",subsections);
            System.out.println(subsections);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/subsection.jsp").forward(req, resp);
            //resp.setCharacterEncoding("UTF-8");
//            resp.setContentType("text/html;charset=UTF-8");
//            printWriter.println("<body>");
//            printWriter.println(subsections);
//            printWriter.println("</body");
////            printWriter.write("<html>" +
////                    "<body>"+subsections+"</body>"+"</html>");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
