package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface AdminService {

    int addClient(UserDto userDto) throws RepeatitionException;//все работает

    void deleteClient(int clientId) throws ClientNotFoundException;//все работает

    UserDto findClientByLogin(String login) throws ClientNotFoundException;//все работает

    UserDto findClientById(int clientId) throws ClientNotFoundException;//все работает

    void changeDiscount(int goodId, double discount) throws GoodNotFoundException, GoodException;//все работает

    List<UserDto> findAllClient();//все работате

    void updateStatus(Status status, PurchaseDto purchaseDto);
}
