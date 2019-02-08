package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Review;

public interface ClientService {

    void addReview(Review review);

    void removeReview(Review review);

    void logIn(String login, String password);

    void logOut();

    void signIn(Client client);

    void changeLogin(String login);

    void changePassword(String password);


}
