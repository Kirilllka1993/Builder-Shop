package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao{

    void addClient(Client client) throws RepeatitionException;

    void deleteClient(int id);

    Client findClientByLogin(String login) throws ClientNotFoundException;

    Client findClientById(int id) throws ClientNotFoundException;

    void changeDiscount(int id, double discount);

    List<Purchase> findAllPurchases();

    List<Client> findAllClient();

    Purchase findPurchasebyId(int id) throws PurchaseNotFoundException;

    List<Good> findAllGoods();

    Purchase updateStatus(Status status, Purchase purchase);
}
