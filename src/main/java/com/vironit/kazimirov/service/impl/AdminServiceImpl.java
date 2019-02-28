package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.AdminDaoImpl;
import com.vironit.kazimirov.dao.DaoInterface.AdminDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao;

    public AdminServiceImpl() {
        adminDao = new AdminDaoImpl();
    }


    @Override
    public void addClient(Client client) throws RepeatitionException {
        adminDao.addClient(client);

    }

    @Override
    public void deleteClient(int id) {
        adminDao.deleteClient(id);

    }

    @Override
    public Client findClientByLogin(String login)  throws ClientNotFoundException{
        return adminDao.findClientByLogin(login);
    }


    @Override
    public Client findClientById(int id) throws ClientNotFoundException {
        return adminDao.findClientById(id);
    }

    @Override
    public void changeDiscount(int id, double discount) {
        adminDao.changeDiscount(id, discount);

    }

    @Override
    public List<Purchase> findAllPurchases() {
        return adminDao.findAllPurchases();

    }

    @Override
    public List<Client> findAllClient() {
        return adminDao.findAllClient();
    }

    @Override
    public Purchase findPurchasebyId(int id) throws PurchaseNotFoundException {
        return adminDao.findPurchasebyId(id);
    }

    @Override
    public List<Good> findAllGoods() {
        return adminDao.findAllGoods();
    }

    @Override
    public Purchase updateStatus(Status status, Purchase purchase) {
        return adminDao.updateStatus(status,purchase);
    }
}
