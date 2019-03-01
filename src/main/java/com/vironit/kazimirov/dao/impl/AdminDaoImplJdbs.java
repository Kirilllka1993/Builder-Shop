package com.vironit.kazimirov.dao.impl;

import com.vironit.kazimirov.entity.Client;

import java.sql.*;

public class AdminDaoImplJdbs {
    private Connection connection;

    public AdminDaoImplJdbs(Connection connection) {
        this.connection = connection;
    }

    public AdminDaoImplJdbs() {
    }

    public void addClient(Client client) throws SQLException {
//        final String URL = "jdbc:mysql://127.0.0.1:3306/shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//        final String USERNAME = "root";
//        final String PASSWORD = "1111";
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        //Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            System.out.println("SqlException ");
//        }
        final String ADD_CLIENT = "INSERT INTO shop.client (`name`, `surname`, `login`, `password`,`adress`,`phoneNumber`)" + "VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_CLIENT);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurname());
        preparedStatement.setString(3, client.getLogin());
        preparedStatement.setString(4, client.getPassword());
        preparedStatement.setString(5, client.getAddress());
        preparedStatement.setString(6, client.getPhoneNumber());
        preparedStatement.execute();
    }

    public void deleteClient(String idClient) throws SQLException {
        final String DELETE_CLIENT="DELETE FROM shop.client WHERE (id = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT);
        preparedStatement.setString(1,idClient);
        preparedStatement.execute();
    }
}
