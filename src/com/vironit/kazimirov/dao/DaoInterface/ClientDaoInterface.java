package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Review;

public interface ClientDaoInterface {
    void addReview(Review review);

    void removeReiew(Review review);

    void logIn();

    void logOut();
}
