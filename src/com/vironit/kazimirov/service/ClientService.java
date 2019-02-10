package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Review;

import java.util.List;

public interface ClientService {

    void addReview(int mark, String text);

    void removeReview(int id);

    void logIn(String login, String password);

    void logOut();

    void signIn(String name, String surname, String login, String password, String adress, String phoneNumber);

    void changeLogin(int id, String login);

    void changePassword(int id, String password);


}
