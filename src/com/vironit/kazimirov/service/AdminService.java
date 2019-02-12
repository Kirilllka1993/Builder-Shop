package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;

import java.util.List;

public interface AdminService {

    void addClient(String name, String surname, String login, String password, String adress, String phoneNumber) throws RepeatitionException;

    void deleteClient(int id);


    Client searchClientByLogin(String login) throws ClientNotFoundException;

    Client searchClientById(int id) throws ClientNotFoundException;

    void changeDiscount(int id, double discount);

    List<Purchase> showAllPurchases();

    List<Client> showAllClient();

    Purchase searchPurchasebyId(int id) throws PurchaseNotFoundException;






}
