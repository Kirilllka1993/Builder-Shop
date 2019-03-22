package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface ClientService {

    void addReview(Review review);

    void removeReview(int clientId, int goodId);

    Client logIn(String login, String password) throws RepeatitionException;

    void logOut();

    void signIn(Client client) throws RepeatitionException, ClientNotFoundException;

    void changeLogin(int clientId, String newLogin) throws RepeatitionException, ClientNotFoundException;

    void changePassword(int clientId, String newPassword);

    void changePhoneNumber (int clientId, String newPhoneNumber);

    void changeAddress(int clientId, String newAddress);

    List<Review> findAllReviews(Client client);


}
