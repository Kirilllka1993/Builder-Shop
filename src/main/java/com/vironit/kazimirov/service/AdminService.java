package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface AdminService {

    int addClient(Client client) throws RepeatitionException;

    void deleteClient(int clientId);

    Client findClientByLogin(String login) throws ClientNotFoundException;

    Client findClientById(int clientId) throws ClientNotFoundException;

    void changeDiscount(int goodId, double discount);

    List<Client> findAllClient();

    void updateStatus(Status status, Purchase purchase);
}
