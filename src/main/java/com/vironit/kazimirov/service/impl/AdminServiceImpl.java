package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;
    private final PurchaseDao purchaseDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao, PurchaseDao purchaseDao) {
        this.adminDao = adminDao;
        this.purchaseDao = purchaseDao;
    }

    @Override
    public void addClient(Client client) throws RepeatitionException{
            adminDao.addClient(client);
    }

    @Override
    public void deleteClient(int clientId) {
        adminDao.deleteClient(clientId);

    }

    @Override
    public Client findClientByLogin(String login)  throws ClientNotFoundException{
        return adminDao.findClientByLogin(login);
    }


    @Override
    public Client findClientById(int clientId) throws ClientNotFoundException {
        return adminDao.findClientById(clientId);
    }

    @Override
    public void changeDiscount(int goodId, double discount) {
        adminDao.changeDiscount(goodId, discount);

    }

//    @Override
//    public List<Purchase> findAllPurchases() {
//        return adminDao.findAllPurchases();
//    }

    @Override
    public List<Client> findAllClient() {
        return adminDao.findAllClient();
    }

//    @Override
//    public Purchase findPurchasebyId(int id) throws PurchaseNotFoundException {
//        return adminDao.findPurchasebyId(id);
//    }

//    @Override
//    public List<Good> findAllGoods() {
//        return adminDao.findAllGoods();
//    }

    @Override
    public void updateStatus(int status, Purchase purchase) {
         adminDao.updateStatus(status,purchase);
    }
}
