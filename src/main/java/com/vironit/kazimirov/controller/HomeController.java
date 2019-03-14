package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dao.impl.GoodDaoImpl;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/main")
public class HomeController {
    private AdminService adminService;

    @RequestMapping("/showSubsection") // method = RequestMethod.GET
    public String showForm(ModelMap map) {
        map.addAttribute("mess", "Hi! Here work Spring");
        //map.addAttribute("clients",adminService.findAllClient());

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
        GoodDaoImpl goodDaoImpl = new GoodDaoImpl(connection);
        List<Subsection> subsections = new ArrayList<>();
        try {
            subsections = goodDaoImpl.showGoods();
            map.addAttribute("subsections", subsections);
            System.out.println(subsections);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

}
