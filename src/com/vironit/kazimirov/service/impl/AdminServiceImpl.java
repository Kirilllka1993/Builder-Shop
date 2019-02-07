package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.AdminDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao;

    @Override
    public void addClient(Client client) {

    }

    @Override
    public void deleteClient(Client client) {

    }

    @Override
    public void addGood(Good good) {
        adminDao.addGood(good);

    }

    @Override
    public void deleteGood(Good good) {

    }

    @Override
    public void makeADiscount(Discount discount) {

    }

    @Override
    public List<Purchase> showAllPurchases() {
        return null;
    }
}
