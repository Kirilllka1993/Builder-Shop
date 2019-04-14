package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseService {

    List<PurchaseDto> findPurchases();

    int createNewPurchase(UserDto userDto) throws ClientNotFoundException;

    PurchaseDto findPurchaseById(int purchaseId) throws PurchaseNotFoundException;

    void makeAPurchase(int purchaseId) throws PurchaseException, PurchaseNotFoundException;

    List<PurchaseDto> findPurchasesByDate(LocalDateTime timeOfPurchase);

    void changeStatus(PurchaseDto purchaseDto, Status status) throws PurchaseNotFoundException;

    void removePurchase(int purchaseId) throws PurchaseNotFoundException, CantDeleteElement;


}
