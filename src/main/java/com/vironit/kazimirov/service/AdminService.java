package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.LinkedList;
import java.util.List;

public interface AdminService {

    int addClient(UserDto userDto) throws RepeatitionException;

    void deleteClient(int clientId) throws ClientNotFoundException;

    UserDto findClientByLogin(String login) throws ClientNotFoundException;

    UserDto findClientById(int clientId) throws ClientNotFoundException;

    void changeDiscount(int goodId, double discount) throws GoodNotFoundException, GoodException;

    List<UserDto> findAllClient();

}
