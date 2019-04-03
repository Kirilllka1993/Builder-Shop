package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface AdminDao{

    int addClient(User user) throws RepeatitionException;

    void deleteClient(int clientId);

    User findClientByLogin(String login);

    User findClientById(int clientId);

    void changeDiscount(int goodId, double discount);

    List<User> findAllClient();

    void updateStatus(Status status, Purchase purchase);
}
