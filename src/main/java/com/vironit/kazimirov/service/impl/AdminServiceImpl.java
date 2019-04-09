package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final AdminDao adminDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PurchaseDao purchaseDao;


    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public int addClient(UserDto userDto) throws RepeatitionException {
        //matcher
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(userDto.getLogin()));
        if (checkLoginClient.isPresent() == false) {
            User user = userDto.createClient();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return adminDao.addClient(user);
        } else {
            throw new RepeatitionException("such login is used");
        }
    }

    @Override
    public void deleteClient(int clientId) {
        adminDao.deleteClient(clientId);

    }

    @Override
    public UserDto findClientByLogin(String login) throws ClientNotFoundException {
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(login));
        if (checkLoginClient.isPresent() == false) {
            throw new ClientNotFoundException("such login is absent");
        } else {
            User user = adminDao.findClientByLogin(login);
            UserDto userDto = new UserDto(user);
            return userDto;
        }
    }


    @Override
    public UserDto findClientById(int clientId) throws ClientNotFoundException {
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientById(clientId));
        if (checkLoginClient.isPresent() == false) {
            throw new ClientNotFoundException("such user is absent");
        } else {
            User user = adminDao.findClientById(clientId);
            UserDto userDto = new UserDto(user);
            return userDto;
        }
    }

    @Override
    public void changeDiscount(int goodId, double discount) {
        adminDao.changeDiscount(goodId, discount);

    }

    @Override
    public List<UserDto> findAllClient() {
        List<User> users = adminDao.findAllClient();
        List<UserDto> userDtos = users.stream().map(UserDto::new).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void updateStatus(Status status, PurchaseDto purchaseDto) {
        Purchase purchase = purchaseDao.findPurchaseById(purchaseDto.getId());
        adminDao.updateStatus(status, purchase);
    }
}
