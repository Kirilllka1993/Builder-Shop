package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Review;

public interface ClientService {

    void addReview(Review review);

    void removeReiew(Review review);

    void logIn();

    void logOut();


}
