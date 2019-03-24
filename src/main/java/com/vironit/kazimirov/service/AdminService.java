package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface AdminService  {

    void addClient(Client client) throws RepeatitionException;

    void deleteClient(int clientId);

    Client findClientByLogin(String login);

    Client findClientById(int clientId);

    void changeDiscount(int goodId, double discount);

    List<Client> findAllClient();

    void updateStatus(int status,Purchase purchase);






}
