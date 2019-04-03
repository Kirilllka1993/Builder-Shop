package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.ClientDto;
import com.vironit.kazimirov.dto.ReviewClientDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.ClientService;
import com.vironit.kazimirov.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/newReview", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@RequestBody ReviewClientDto reviewClientDto) throws ClientNotFoundException, GoodNotFoundException {

        User user = adminService.findClientById(reviewClientDto.getClientId());
        Good good = goodService.findGoodById(reviewClientDto.getGoodId());
        Review review = new Review();
        review.setMark(reviewClientDto.getMark());
        review.setComment(reviewClientDto.getComment());
        review.setGood(good);
        review.setUser(user);
        clientService.addReview(review);
    }

    @RequestMapping(value = "/deleteReview",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@RequestBody ReviewClientDto reviewClientDto) {
        clientService.removeReview(reviewClientDto.getClientId(), reviewClientDto.getGoodId());
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User logIn(@RequestBody ClientDto clientDto) throws ClientNotFoundException {
        User user = clientService.logIn(clientDto.getLogin(), clientDto.getPassword());
        return user;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int signIn(@RequestBody ClientDto clientDto) throws RepeatitionException {
        User user = clientDto.createClient();
        int clientId=clientService.signIn(user);
        return clientId;
    }

    @RequestMapping(value = "/newLogin", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeLogin(@RequestBody ClientDto clientDto) throws RepeatitionException {
            clientService.changeLogin(clientDto.getId(), clientDto.getLogin());
    }

//    @RequestMapping(value = "/newPassword", method = RequestMethod.PUT)
//    public void changePassword(@RequestBody ObjectNode objectNode) {
//        int clientId=objectNode.get("clientId").asInt();
//        String password=objectNode.get("password").asText();
//        clientService.changePassword(clientId,password);
//    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody ClientDto clientDto) {
        clientService.changePassword(clientDto.getId(), clientDto.getPassword());
    }

    @RequestMapping(value = "/newPhoneNumber", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePhoneNumber(@RequestBody ClientDto clientDto) {
        clientService.changePhoneNumber(clientDto.getId(), clientDto.getPassword());
    }

    @RequestMapping(value = "/newAddress", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAddress(@RequestBody ClientDto clientDto) {
        clientService.changeAddress(clientDto.getId(), clientDto.getAddress());
    }

    @RequestMapping(value = "/allReviews", method = RequestMethod.GET)
    public List<Review> findAllReviews(@RequestBody ReviewClientDto reviewClientDto) throws ClientNotFoundException {
        User user =adminService.findClientById(reviewClientDto.getClientId());
        List<Review>reviews=clientService.findAllReviews(user);
        return reviews;
    }

}
