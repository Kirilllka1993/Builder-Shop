package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;

import java.util.List;

public interface AdminDao{

    int addClient(Client client) throws RepeatitionException;

    void deleteClient(int clientId);

    Client findClientByLogin(String login);

    Client findClientById(int clientId);

    void changeDiscount(int goodId, double discount);

    List<Client> findAllClient();

    void updateStatus(Status status, Purchase purchase);
}
