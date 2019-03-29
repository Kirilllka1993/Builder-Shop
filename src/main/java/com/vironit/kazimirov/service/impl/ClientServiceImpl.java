package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientDao clientDao;
    @Autowired
    private final AdminDao adminDao;

    @Autowired
    public ClientServiceImpl(ClientDao clientDao, AdminDao adminDao) {
        this.clientDao = clientDao;
        this.adminDao = adminDao;
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
    public Client logIn(String login, String password) throws ClientNotFoundException {
        //matcher
        //Optional<Client> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(login));
        Optional<Client> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(login));
        if (checkLoginClient.isPresent() == false) {
            throw new ClientNotFoundException("such login is absent");
        } else {
            return clientDao.logIn(login, password);
        }
    }


    @Override
    public void logOut() {// это не в дао

    }

    @Override
    public void signIn(Client client) throws RepeatitionException {
        //matcher
        Optional<Client> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(client.getLogin()));
        if (checkLoginClient.isPresent() == false) {
            clientDao.signIn(client);
        } else {
            throw new RepeatitionException("such login is used");
        }
    }

    @Override
    public void changeLogin(int clientId, String newLogin) throws RepeatitionException {
        //matcher
        Optional<Client> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(newLogin));
        if ((checkLoginClient.isPresent() == false)) {
            clientDao.changeLogin(clientId, newLogin);
        } else {
            throw new RepeatitionException("such login is used");
        }


    }

    @Override
    public void changePassword(int clientId, String newPassword) {
        //matcher
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

    @Override
    public List<Review> findAllReviews(Client client) {
        return clientDao.findAllReviews(client);
    }
}
