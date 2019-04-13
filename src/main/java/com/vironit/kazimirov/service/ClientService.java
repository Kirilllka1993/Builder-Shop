package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.ReviewNotFoundException;

import java.util.List;

public interface ClientService {

    int addReview(ReviewDto reviewDto) throws ClientNotFoundException, GoodNotFoundException;

    void removeReview(int clientId, int goodId) throws ClientNotFoundException, GoodNotFoundException, ReviewNotFoundException;

    ReviewDto findReview(int clientId, int goodId) throws ClientNotFoundException;

    ReviewDto findReviewById(int reviewId) throws ReviewNotFoundException;

    UserDto logIn(String login, String password) throws ClientNotFoundException;

    void logOut();

    int signIn(UserDto userDto) throws RepeatitionException;

    void changeLogin(int clientId, String newLogin) throws RepeatitionException, ClientNotFoundException;

    void changePassword(int clientId, String newPassword) throws ClientNotFoundException;

    void changePhoneNumber (int clientId, String newPhoneNumber) throws ClientNotFoundException;

    void changeAddress(int clientId, String newAddress) throws ClientNotFoundException;

    List<ReviewDto> findAllReviewsByUser(UserDto userDto);

    List<ReviewDto>findAllReviews();


}
