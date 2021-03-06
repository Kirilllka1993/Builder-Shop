package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface ClientDao {
    int addReview(Review review);

    void removeReview(int clientId, int goodId);

    Review findReview(int clientId, int goodId);

    User logIn(String login, String password) throws ClientNotFoundException;

    void logOut();

    int signIn(User user) throws RepeatitionException;

    void changeLogin(int clientId, String newLogin) throws RepeatitionException;

    void changePassword(int clientId, String newPassword);

    void changePhoneNumber(int clientId, String newPhoneNumber);

    void changeAddress(int clientId, String newAddress);

    List<Review> findAllReviewsByUser(User user);

    Review findReviewById(int reviewId);

    List<Review> findAllReviews();
}
