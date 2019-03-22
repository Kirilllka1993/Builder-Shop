package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private AdminService adminService;

    @Autowired
    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void addReview(Review review) {
        clientDao.addReview(review);

    }

    @Override
    public void removeReview(int clientId, int goodId) {
        clientDao.removeReview(clientId, goodId);

    }

    @Override
    public Client logIn(String login, String password) throws RepeatitionException {
        return clientDao.logIn(login, password);

    }


    @Override
    public void logOut() {// это не в дао

    }

    @Override
    public void signIn(Client client) throws RepeatitionException {
        clientDao.signIn(client);

    }

    @Override
    public void changeLogin(int clientId, String newLogin) throws RepeatitionException {
        clientDao.changeLogin(clientId, newLogin);

    }

    @Override
    public void changePassword(int clientId, String newPassword) {
        clientDao.changePassword(clientId, newPassword);

    }

    @Override
    public void changePhoneNumber(int clientId, String newPhoneNumber) {
        clientDao.changePhoneNumber(clientId, newPhoneNumber);
    }

    @Override
    public void changeAddress(int clientId, String newAddress) {
        clientDao.changeAddress(clientId, newAddress);

    }

//    @Override
//    public List<Client> findAllClients() {
//        return clientDao.findAllClients();
//    }

    @Override
    public List<Review> findAllReviews(Client client) {
        return clientDao.findAllReviews(client);
    }
}
