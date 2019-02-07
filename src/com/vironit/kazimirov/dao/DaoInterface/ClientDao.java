package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Review;

public interface ClientDao {
    void addReview(Review review);

    void removeReiew(Review review);

    void logIn();

    void logOut();
}
