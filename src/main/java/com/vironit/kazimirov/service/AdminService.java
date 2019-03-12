package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AdminService  {

    void addClient(Client client) throws RepeatitionException, SQLException;

    void deleteClient(int idClient) throws SQLException;

    Client findClientByLogin(String login) throws ClientNotFoundException;

    Client findClientById(int id) throws ClientNotFoundException;

    void changeDiscount(int id, double discount);

    List<Purchase> findAllPurchases();

    List<Client> findAllClient() throws SQLException;

    Purchase findPurchasebyId(int id) throws PurchaseNotFoundException;

    List<Good> findAllGoods();

    Purchase updateStatus(Status status,Purchase purchase);






}
