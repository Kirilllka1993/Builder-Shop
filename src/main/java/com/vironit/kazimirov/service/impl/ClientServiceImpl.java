package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientDao clientDao;
    @Autowired
    private final AdminDao adminDao;
    @Autowired
    private GoodDao goodDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientDao clientDao, AdminDao adminDao) {
        this.clientDao = clientDao;
        this.adminDao = adminDao;
    }

    @Override
    public int addReview(ReviewDto reviewDto) throws ClientNotFoundException, GoodNotFoundException {
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(reviewDto.getGoodId()));
        Optional<User> checkIdClient = Optional.ofNullable(adminDao.findClientById(reviewDto.getUserId()));
        Optional<Review> checkReview = Optional.ofNullable(clientDao.findReview(reviewDto.getUserId(),reviewDto.getGoodId()));
        if ((checkIdClient.isPresent() == false)) {
            throw new ClientNotFoundException();
        } else if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException();
        }else if(checkReview.isPresent() == true){
            throw new ClientNotFoundException("Such review is create ");
        } else {
            User user = adminDao.findClientById(reviewDto.getUserId());
            Good good = goodDao.findGoodById(reviewDto.getGoodId());
            Review review = new Review();
            review.setMark(reviewDto.getMark());
            review.setComment(reviewDto.getComment());
            review.setGood(good);
            review.setUser(user);
            return clientDao.addReview(review);
        }
    }

    @Override
    public void removeReview(int clientId, int goodId) throws ClientNotFoundException, GoodNotFoundException {
        Optional<Review> checkReview = Optional.ofNullable(clientDao.findReview(clientId, goodId));
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        Optional<User> checkIdClient = Optional.ofNullable(adminDao.findClientById(clientId));
        if ((checkIdClient.isPresent() == false || checkReview.isPresent() == false)) {
            throw new ClientNotFoundException();
        } else if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException();
        } else {
            clientDao.removeReview(clientId, goodId);
        }
    }

    @Override
    public ReviewDto findReview(int clientId, int goodId) throws ClientNotFoundException {
        Optional<Review> checkReview = Optional.ofNullable(clientDao.findReview(clientId, goodId));
        if ((checkReview.isPresent() == false)) {
            throw new ClientNotFoundException();
        } else {
            Review review = clientDao.findReview(clientId, goodId);
            ReviewDto reviewDto = new ReviewDto(review);
            return reviewDto;
        }
    }

    @Override
    public UserDto logIn(String login, String password) throws ClientNotFoundException {
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(login));
        if (checkLoginClient.isPresent() == false) {
            throw new ClientNotFoundException("such login is absent");
        } else {
            //String passwordFor=passwordEncoder.encode(password);
            User user = clientDao.logIn(login, password);
            UserDto userDto = new UserDto(user);
            return userDto;
        }
    }


    @Override
    public void logOut() {

    }

    @Override
    public int signIn(UserDto userDto) throws RepeatitionException {
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(userDto.getLogin()));
        if (checkLoginClient.isPresent() == false) {
            User user = userDto.createClient();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return clientDao.signIn(user);
        } else {
            throw new RepeatitionException("such login is used");
        }
    }

    @Override
    public void changeLogin(int clientId, String newLogin) throws RepeatitionException, ClientNotFoundException {
        Optional<User> checkIdClient = Optional.ofNullable(adminDao.findClientById(clientId));
        Optional<User> checkLoginClient = Optional.ofNullable(adminDao.findClientByLogin(newLogin));
        if ((checkIdClient.isPresent() == false)) {
            throw new ClientNotFoundException();
        }
        if ((checkLoginClient.isPresent() == false)) {
            clientDao.changeLogin(clientId, newLogin);
        } else {
            throw new RepeatitionException("such login is used");
        }


    }

    @Override
    public void changePassword(int clientId, String newPassword) throws ClientNotFoundException {
        Optional<User> checkIdClient = Optional.ofNullable(adminDao.findClientById(clientId));
        if ((checkIdClient.isPresent() == false)) {
            throw new ClientNotFoundException();
        } else {
            newPassword = passwordEncoder.encode(newPassword);
            clientDao.changePassword(clientId, newPassword);
        }
    }

    @Override
    public void changePhoneNumber(int clientId, String newPhoneNumber) throws ClientNotFoundException {
        Optional<User> checkIdClient = Optional.ofNullable(adminDao.findClientById(clientId));
        if ((checkIdClient.isPresent() == false)) {
            throw new ClientNotFoundException();
        } else {
            clientDao.changePhoneNumber(clientId, newPhoneNumber);
        }
    }

    @Override
    public void changeAddress(int clientId, String newAddress) throws ClientNotFoundException {
        Optional<User> checkIdClient = Optional.ofNullable(adminDao.findClientById(clientId));
        if ((checkIdClient.isPresent() == false)) {
            throw new ClientNotFoundException();
        } else {
            clientDao.changeAddress(clientId, newAddress);
        }
    }

    @Override
    public List<ReviewDto> findAllReviews(UserDto userDto) {
        User user = adminDao.findClientById(userDto.getId());
        List<Review> reviews = clientDao.findAllReviews(user);
        List<ReviewDto> reviewDtos = reviews.stream().map(ReviewDto::new).collect(Collectors.toList());
        return reviewDtos;
    }
}
