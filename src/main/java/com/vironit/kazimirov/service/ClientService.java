package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface ClientService {

    void addReview(ReviewDto reviewDto) throws ClientNotFoundException, GoodNotFoundException;//все работает

    void removeReview(int clientId, int goodId) throws ClientNotFoundException, GoodNotFoundException;//все работает

    ReviewDto findReview(int clientId, int goodId) throws ClientNotFoundException;

    UserDto logIn(String login, String password) throws ClientNotFoundException;//не работает

    void logOut();

    int signIn(UserDto userDto) throws RepeatitionException;//все работает

    void changeLogin(int clientId, String newLogin) throws RepeatitionException, ClientNotFoundException;//все раблотает

    void changePassword(int clientId, String newPassword) throws ClientNotFoundException;//все раблотает

    void changePhoneNumber (int clientId, String newPhoneNumber) throws ClientNotFoundException;//все раблотает

    void changeAddress(int clientId, String newAddress) throws ClientNotFoundException;//все раблотает

    List<ReviewDto> findAllReviews(UserDto userDto);//все раблотает


}
