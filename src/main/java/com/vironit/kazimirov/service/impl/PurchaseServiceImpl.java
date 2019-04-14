package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.service.CartItemService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vironit.kazimirov.entity.Status.CANCELED;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private final PurchaseDao purchaseDao;
    @Autowired
    private final GoodDao goodDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private CartItemService cartItemService;


    @Autowired
    public PurchaseServiceImpl(PurchaseDao purchaseDao, GoodDao goodDao) {
        this.purchaseDao = purchaseDao;
        this.goodDao = goodDao;
    }

    @Override
    public List<PurchaseDto> findPurchases() {
        List<Purchase> purchases = purchaseDao.findPurchases();
        List<PurchaseDto> purchaseDtos = purchases.stream().map(PurchaseDto::new).collect(Collectors.toList());
        return purchaseDtos;
    }

    @Override
    public int createNewPurchase(UserDto userDto) throws ClientNotFoundException {
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientById(userDto.getId()));
        if (checkLoginClient.isPresent() == false) {
            throw new ClientNotFoundException("such user is absent");
        } else {
            User user = adminDao.findClientById(userDto.getId());
            return purchaseDao.createNewPurchase(user);
        }
    }

    @Override
    public PurchaseDto findPurchaseById(int purchaseId) throws PurchaseNotFoundException {
        Optional<Purchase> checkIdPurchase = Optional.ofNullable(purchaseDao.findPurchaseById(purchaseId));
        if (checkIdPurchase.isPresent() == false) {
            throw new PurchaseNotFoundException("such purchase is absent");
        } else {
            Purchase purchase = purchaseDao.findPurchaseById(purchaseId);
            PurchaseDto purchaseDto = new PurchaseDto(purchase);
            return purchaseDto;
        }
    }


    @Override
    public void makeAPurchase(int purchaseId) throws PurchaseException, PurchaseNotFoundException {
        Optional<Purchase> checkIdPurchase = Optional.ofNullable(purchaseDao.findPurchaseById(purchaseId));
        if (checkIdPurchase.isPresent() == false) {
            throw new PurchaseNotFoundException("such purchase is absent");
        } else {
            Purchase purchase = purchaseDao.findPurchaseById(purchaseId);
            if (purchase.getStatus().equals(CANCELED)) {
                throw new PurchaseException("The purchase is canceled");
            }
            purchaseDao.makeAPurchase(purchaseId);
        }
    }

    @Override
    public List<PurchaseDto> findPurchasesByDate(LocalDateTime timeOfPurchase) {
        List<Purchase> purchases = purchaseDao.findPurchasesByDate(timeOfPurchase);
        List<PurchaseDto> purchaseDtos = purchases.stream().map(PurchaseDto::new).collect(Collectors.toList());
        return purchaseDtos;
    }

    @Override
    public void changeStatus(PurchaseDto purchaseDto, Status status) throws PurchaseNotFoundException {
        Optional<Purchase> checkIdPurchase = Optional.ofNullable(purchaseDao.findPurchaseById(purchaseDto.getId()));
        if (checkIdPurchase.isPresent() == false) {
            throw new PurchaseNotFoundException("such purchase is absent");
        } else {
            Purchase purchase = purchaseDao.findPurchaseById(purchaseDto.getId());
            purchaseDao.changeStatus(purchase, status);
        }
    }

    @Override
    public void removePurchase(int purchaseId) throws PurchaseNotFoundException, CantDeleteElement {
        Optional<Purchase> checkIdPurchase = Optional.ofNullable(purchaseDao.findPurchaseById(purchaseId));
        List<CartItemDto> cartItemDtos = cartItemService.findCartItemsByPurchase(purchaseId);
        if (checkIdPurchase.isPresent() == false) {
            throw new PurchaseNotFoundException("such purchase is absent");
        } else if (cartItemDtos.size() == 0) {
            purchaseDao.removePurchase(purchaseId);
        } else {
            throw new CantDeleteElement("this purchase is using by cartItem");
        }
    }
}
