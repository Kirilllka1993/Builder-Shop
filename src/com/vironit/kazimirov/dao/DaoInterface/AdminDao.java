package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;

import java.util.List;

public interface AdminDao {
    void addClient(String name, String surname, String login, String password, String adress, String phoneNumber);

    void deleteClient(int id);

    Client searchClientByLogin(String login);

    void changeDiscount(int id, double discount);

    List<Purchase> showAllPurchases();

    List<Client> showAllClient();

    Purchase searchPurchasebyId(int id);
}
