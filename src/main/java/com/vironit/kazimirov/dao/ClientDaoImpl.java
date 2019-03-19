package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ClientDaoImpl implements ClientDao {
    @Override
    public void addReview(Review review) {

    }

    @Override
    public void removeReview(int id, Client client) {

    }

    @Override
    public void logIn(String login, String password) throws RepeatitionException {

    }

    @Override
    public void logOut() {

    }

    @Override
    public void signIn(Client client) throws RepeatitionException {

    }

    @Override
    public void changeLogin(int id, String login) throws RepeatitionException {

    }

    @Override
    public void changePassword(int id, String password) {

    }

    @Override
    public void changePhoneNumber(int id, String phoneNumber) {

    }

    @Override
    public void changeAddress(int id, String adress) {

    }

    @Override
    public List<Client> findAllClients() {
        return null;
    }

    @Override
    public List<Review> findAllReviews(Client client) {
        return null;
    }
}
