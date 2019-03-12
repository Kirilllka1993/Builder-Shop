package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.AdminDaoImpl;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao;

    @Autowired
    public AdminServiceImpl(@Qualifier("adminDaoImplJdbs") final AdminDao adminDao) {
        this.adminDao = adminDao;
    }


    @Override
    public void addClient(Client client) throws RepeatitionException, SQLException {
            adminDao.addClient(client);
    }

    @Override
    public void deleteClient(int idClient) throws SQLException {
        adminDao.deleteClient(idClient);

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
    public List<Client> findAllClient() throws SQLException {
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
