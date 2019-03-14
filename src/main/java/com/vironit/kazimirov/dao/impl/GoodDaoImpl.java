package com.vironit.kazimirov.dao.impl;

import com.vironit.kazimirov.entity.Subsection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodDaoImpl {
    private Connection connection;

    public GoodDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Subsection> showGoods() throws SQLException {
//        final String SHOW_GOODS = "SELECT*FROM shop.good";
//        PreparedStatement preparedStatement = connection.prepareStatement(SHOW_GOODS);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        List<Good> goods=new ArrayList<>();
//        while (resultSet.next()) {
//            Good good = new Good();
//            good.setId(resultSet.getInt("id"));
//            good.setQuantity(resultSet.getInt("quantity"));
//            good.setDiscount(resultSet.getInt("Discount_id"));
//            good.setName(resultSet.getString("name"));
//            good.setPrice(resultSet.getDouble("price"));
//            good.setPurpose(resultSet.getInt();
//            good.setSubsection(resultSet.getString("Subsection_id"));
//            goods.add(good);
//        }
        final String SHOW_SUBSECTIONS = "SELECT*FROM shop.subsection";
        PreparedStatement preparedStatement = connection.prepareStatement(SHOW_SUBSECTIONS);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Subsection> subsections =new ArrayList<>();
        while (resultSet.next()){
            Subsection subsection=new Subsection();
            subsection.setId(resultSet.getInt("id"));
            subsection.setTitle(resultSet.getString("title"));
            subsections.add(subsection);
        }
        return subsections;

    }
}
