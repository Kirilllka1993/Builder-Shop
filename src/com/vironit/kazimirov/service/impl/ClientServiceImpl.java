package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.ClientDaoImpl;
import com.vironit.kazimirov.dao.DaoInterface.ClientDao;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.ClientService;

public class ClientServiceImpl implements ClientService {
    @Override
    public void addReview(int mark, String text) {
        ClientDao clientDao = new ClientDaoImpl();
        clientDao.addReview(mark, text);

    }

    @Override
    public void removeReview(int id) {
        ClientDao clientDao = new ClientDaoImpl();
        clientDao.removeReview(id);

    }

    @Override
    public void logIn(String login, String password) throws RepeatitionException {
        ClientDao clientDao = new ClientDaoImpl();
        clientDao.logIn(login,password);

    }


    @Override
    public void logOut() {// это не в дао

    }

    @Override
    public void signIn(String name, String surname, String login, String password, String adress, String phoneNumber) {
        ClientDao clientDao = new ClientDaoImpl();
        clientDao.signIn(name, surname, login, password, adress, phoneNumber);

    }

    @Override
    public void changeLogin(int id, String login) {
        ClientDao clientDao = new ClientDaoImpl();
        clientDao.changeLogin(id, login);

    }

    @Override
    public void changePassword(int id, String password) {
        ClientDao clientDao = new ClientDaoImpl();
        clientDao.changePassword(id, password);

    }
}
