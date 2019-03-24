package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public void addClient(Client client) throws RepeatitionException{
        //matcher
        Optional<Client> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(client.getLogin()));
            if (checkLoginClient.isPresent()==false){
                adminDao.addClient(client);
        }else{
                throw new RepeatitionException("such login is used");
        }
    }

    @Override
    public void deleteClient(int clientId) {
        adminDao.deleteClient(clientId);

    }

    @Override
    public Client findClientByLogin(String login) {
        return adminDao.findClientByLogin(login);
    }


    @Override
    public Client findClientById(int clientId){
        return adminDao.findClientById(clientId);
    }

    @Override
    public void changeDiscount(int goodId, double discount) {
        adminDao.changeDiscount(goodId, discount);

    }

    @Override
    public List<Client> findAllClient() {
        return adminDao.findAllClient();
    }

    @Override
    public void updateStatus(int status, Purchase purchase) {
         adminDao.updateStatus(status,purchase);
    }
}
