package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface ClientService {

    void addReview(Review review);

    void removeReview(int id, Client client);

    Client logIn(String login, String password) throws RepeatitionException;

    void logOut();

    void signIn(Client client) throws RepeatitionException;

    void changeLogin(int id, String login) throws RepeatitionException;

    void changePassword(int id, String password);

    void changePhoneNumber (int id, String phoneNumber);

    void changeAddress(int id, String address);

    List<Client> findAllClients();

    List<Review> findAllReviews(Client client);


}
