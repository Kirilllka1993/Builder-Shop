package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface ClientService {

    void addReview(ReviewDto reviewDto);//

    void removeReview(int clientId, int goodId);

    UserDto logIn(String login, String password) throws ClientNotFoundException;

    void logOut();

    int signIn(UserDto userDto) throws RepeatitionException;

    void changeLogin(int clientId, String newLogin) throws RepeatitionException;

    void changePassword(int clientId, String newPassword);

    void changePhoneNumber (int clientId, String newPhoneNumber);

    void changeAddress(int clientId, String newAddress);

    List<Review> findAllReviews(UserDto userDto);


}
