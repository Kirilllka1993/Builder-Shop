package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface AdminService {

    int addClient(User user) throws RepeatitionException;

    void deleteClient(int clientId);

    User findClientByLogin(String login) throws ClientNotFoundException;

    User findClientById(int clientId) throws ClientNotFoundException;

    void changeDiscount(int goodId, double discount);

    List<User> findAllClient();

    void updateStatus(Status status, Purchase purchase);
}
