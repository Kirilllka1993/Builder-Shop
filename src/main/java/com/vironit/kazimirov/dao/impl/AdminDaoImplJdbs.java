package com.vironit.kazimirov.dao.impl;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AdminDaoImplJdbs implements AdminDao {
    private Connection connection;

//    public AdminDaoImplJdbs(Connection connection) {
//        this.connection = connection;
//    }

    public AdminDaoImplJdbs() {
    }

    private Connection connectToDataBase(){
        final String URL = "jdbc:mysql://127.0.0.1:3306/shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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
        return connection;
    }

    public void addClient(Client client) throws  SQLException{
        AdminDaoImplJdbs adminDaoImplJdbs=new AdminDaoImplJdbs();
        connection=adminDaoImplJdbs.connectToDataBase();
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

    @Override
    public Client findClientByLogin(String login) throws ClientNotFoundException {
        return null;
    }

    @Override
    public Client findClientById(int id) throws ClientNotFoundException {
        return null;
    }

    @Override
    public void changeDiscount(int id, double discount) {

    }

    @Override
    public List<Purchase> findAllPurchases() {
        return null;
    }

    @Override
    public List<Client> findAllClient() throws SQLException {
        AdminDaoImplJdbs adminDaoImplJdbs=new AdminDaoImplJdbs();
        connection=adminDaoImplJdbs.connectToDataBase();
        final String SHOW_CLIENTS = "SELECT*FROM shop.client";
        PreparedStatement preparedStatement = connection.prepareStatement(SHOW_CLIENTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Client> clients =new ArrayList<>();
        while (resultSet.next()){
            Client client=new Client();
            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("name"));
            client.setSurname(resultSet.getString("surname"));
            client.setLogin(resultSet.getString("login"));
            client.setPassword(resultSet.getString("password"));
            client.setAddress(resultSet.getString("adress"));
            client.setPhoneNumber(resultSet.getString("phoneNumber"));
            clients.add(client);
        }
        return clients;
    }

    @Override
    public Purchase findPurchasebyId(int id) throws PurchaseNotFoundException {
        return null;
    }

    @Override
    public List<Good> findAllGoods() {
        return null;
    }

    @Override
    public Purchase updateStatus(Status status, Purchase purchase) {
        return null;
    }

    public void deleteClient(int idClient) throws SQLException {
        AdminDaoImplJdbs adminDaoImplJdbs=new AdminDaoImplJdbs();
        connection=adminDaoImplJdbs.connectToDataBase();
        final String DELETE_CLIENT="DELETE FROM shop.client WHERE (id = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT);
        preparedStatement.setInt(1,idClient);
        preparedStatement.execute();
    }
}
