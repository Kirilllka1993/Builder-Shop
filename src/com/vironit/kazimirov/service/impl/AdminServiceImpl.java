package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.AdminDaoImpl;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AdminDaoImpl adminDao;


    @Override
    public void addClient(String name, String surname, String login, String password, String adress, String phoneNumber) throws RepeatitionException {
        AdminDaoImpl adminDao=new AdminDaoImpl();
        adminDao.addClient(name,surname,login,password,adress,phoneNumber);

    }

    @Override
    public void deleteClient(int id) {
        AdminDaoImpl adminDao=new AdminDaoImpl();
        adminDao.deleteClient(id);

    }

    @Override
    public Client searchClientByLogin(String login) throws ClientNotFoundException {
        AdminDaoImpl adminDao=new AdminDaoImpl();
        return adminDao.searchClientByLogin(login);

    }

    @Override
    public Client searchClientById(int id) throws ClientNotFoundException {
        AdminDaoImpl adminDao=new AdminDaoImpl();
        return adminDao.searchClientById(id);
    }

    @Override
    public void changeDiscount(int id, double discount) {
        AdminDaoImpl adminDao=new AdminDaoImpl();
        adminDao.changeDiscount(id, discount);

    }

    @Override
    public List<Purchase> showAllPurchases() {
        AdminDaoImpl adminDao=new AdminDaoImpl();
        return adminDao.showAllPurchases();

    }

    @Override
    public List<Client> showAllClient() {
        AdminDaoImpl adminDao=new AdminDaoImpl();
        return adminDao.showAllClient();
    }

    @Override
    public Purchase searchPurchasebyId(int id) throws PurchaseNotFoundException {
        AdminDaoImpl adminDao=new AdminDaoImpl();
        return adminDao.searchPurchasebyId(id);

    }
}
