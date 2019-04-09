package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientDao clientDao;
    @Autowired
    private final AdminDao adminDao;
    @Autowired
    private GoodDao goodDao;

    @Autowired
    public ClientServiceImpl(ClientDao clientDao, AdminDao adminDao) {
        this.clientDao = clientDao;
        this.adminDao = adminDao;
    }

    @Override
    public void addReview(ReviewDto reviewDto) {
        User user = adminDao.findClientById(reviewDto.getUserId());
        Good good = goodDao.findGoodById(reviewDto.getGoodId());
        Review review = new Review();
        review.setMark(reviewDto.getMark());
        review.setComment(reviewDto.getComment());
        review.setGood(good);
        review.setUser(user);
        clientDao.addReview(review);

    }

    @Override
    public void removeReview(int clientId, int goodId) {
        clientDao.removeReview(clientId, goodId);

    }

    @Override
    public UserDto logIn(String login, String password) throws ClientNotFoundException {
        //matcher
        //Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(login));
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(login));
        if (checkLoginClient.isPresent() == false) {
            throw new ClientNotFoundException("such login is absent");
        } else {
            User user=clientDao.logIn(login, password);
            UserDto userDto=new UserDto(user);
            return userDto;
        }
    }


    @Override
    public void logOut() {// это не в дао

    }

    @Override
    public int signIn(UserDto userDto) throws RepeatitionException {
        //matcher
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(userDto.getLogin()));
        if (checkLoginClient.isPresent() == false) {
            User user=userDto.createClient();
           return clientDao.signIn(user);
        } else {
            throw new RepeatitionException("such login is used");
        }
    }

    @Override
    public void changeLogin(int clientId, String newLogin) throws RepeatitionException {
        //matcher
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(newLogin));
        if ((checkLoginClient.isPresent() == false)) {
            clientDao.changeLogin(clientId, newLogin);
        } else {
            throw new RepeatitionException("such login is used");
        }


    }

    @Override
    public void changePassword(int clientId, String newPassword) {
        //matcher
        clientDao.changePassword(clientId, newPassword);

    }

    @Override
    public void changePhoneNumber(int clientId, String newPhoneNumber) {
        clientDao.changePhoneNumber(clientId, newPhoneNumber);
    }

    @Override
    public void changeAddress(int clientId, String newAddress) {
        clientDao.changeAddress(clientId, newAddress);

    }

    @Override
    public List<Review> findAllReviews(UserDto userDto) {
        User user=adminDao.findClientById(userDto.getId());
        return clientDao.findAllReviews(user);
    }
}
