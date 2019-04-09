package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;


import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseService {

    List<PurchaseDto> findPurchases();//выполнено


    int createNewPurchase(UserDto userDto) throws ClientNotFoundException;//пользователь

    PurchaseDto findPurchaseById(int purchaseId);//админ

    void makeAPurchase(int purchaseId) throws PurchaseException;//пользователь

    List<PurchaseDto> findPurchasesByDate(LocalDateTime timeOfPurchase);//админ

    void changeStatus(PurchaseDto purchaseDto, Status status);//пользователь

    void removePurchase(int purchaseId);//выполнено


}
