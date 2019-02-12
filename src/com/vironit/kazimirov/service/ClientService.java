package com.vironit.kazimirov.service;

import com.vironit.kazimirov.exception.RepeatitionException;

public interface ClientService {

    void addReview(int mark, String text);

    void removeReview(int id);

    void logIn(String login, String password) throws RepeatitionException;

    void logOut();

    void signIn(String name, String surname, String login, String password, String adress, String phoneNumber);

    void changeLogin(int id, String login);

    void changePassword(int id, String password);


}
