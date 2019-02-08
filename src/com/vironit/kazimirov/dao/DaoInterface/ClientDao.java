package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;

public interface ClientDao {
    void addReview(Review review);

    void removeReview(Review review);

    void logIn(String login, String password);

    void logOut();

    void signIn(Client client);

    void changeLogin(String login);

    void changePassword(String password);
}
