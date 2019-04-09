package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface AdminService {

    int addClient(UserDto userDto) throws RepeatitionException;

    void deleteClient(int clientId);

    UserDto findClientByLogin(String login) throws ClientNotFoundException;

    UserDto findClientById(int clientId) throws ClientNotFoundException;

    void changeDiscount(int goodId, double discount);

    List<UserDto> findAllClient();

    void updateStatus(Status status, PurchaseDto purchaseDto);
}
