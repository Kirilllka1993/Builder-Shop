package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.ClientDaoImpl;
import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;
    @Autowired
    public ClientServiceImpl() {
        clientDao = new ClientDaoImpl();
    }

    @Override
    public void addReview(Review review) {
        clientDao.addReview(review);

    }

    @Override
    public void removeReview(int id, Client client) {
        clientDao.removeReview(id,client);

    }

    @Override
    public void logIn(String login, String password) throws RepeatitionException {
        clientDao.logIn(login, password);

    }


    @Override
    public void logOut() {// это не в дао

    }

    @Override
    public void signIn(Client client) throws RepeatitionException {
        clientDao.signIn(client);

    }

    @Override
    public void changeLogin(int id, String login) throws RepeatitionException {
        clientDao.changeLogin(id, login);

    }

    @Override
    public void changePassword(int id, String password) {
        clientDao.changePassword(id, password);

    }

    @Override
    public void changePhoneNumber(int id, String phoneNumber) {
        clientDao.changePhoneNumber(id, phoneNumber);
    }

    @Override
    public void changeAddress(int id, String adress) {
        clientDao.changeAddress(id, adress);

    }

    @Override
    public List<Client> findAllClients() {
        return clientDao.findAllClients();
    }

    @Override
    public List<Review> findAllReviews(Client client) {
        return clientDao.findAllReviews(client);
    }
}
