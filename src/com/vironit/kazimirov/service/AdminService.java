package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;

import java.util.List;

public interface AdminService {

    void addClient(Client client);

    void deleteClient(Client client);

    void addGood(Good good);

    void deleteGood(Good good);

    void makeADiscount(Discount discount);

    List<Purchase> showAllPurchases();


}
