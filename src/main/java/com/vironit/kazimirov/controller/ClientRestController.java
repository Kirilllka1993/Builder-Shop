package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.dto.UserDto;
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
    public void addReview(@RequestBody ReviewDto reviewDto) throws ClientNotFoundException, GoodNotFoundException {
//

        clientService.addReview(reviewDto);
    }

    @RequestMapping(value = "/deleteReview",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@RequestBody ReviewClientDto reviewClientDto) {
        clientService.removeReview(reviewClientDto.getClientId(), reviewClientDto.getGoodId());
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDto logIn(@RequestBody UserDto userDto) throws ClientNotFoundException {
        UserDto user = clientService.logIn(userDto.getLogin(), userDto.getPassword());
        return user;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int signIn(@RequestBody UserDto userDto) throws RepeatitionException {
        //User user = userDto.createClient();
        int clientId=clientService.signIn(userDto);
        return clientId;
    }

    @RequestMapping(value = "/newLogin", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeLogin(@RequestBody UserDto userDto) throws RepeatitionException {
            clientService.changeLogin(userDto.getId(), userDto.getLogin());
    }

//    @RequestMapping(value = "/newPassword", method = RequestMethod.PUT)
//    public void changePassword(@RequestBody ObjectNode objectNode) {
//        int userId=objectNode.get("userId").asInt();
//        UserRoleEnum password=objectNode.get("password").asText();
//        clientService.changePassword(userId,password);
//    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody UserDto userDto) {
        clientService.changePassword(userDto.getId(), userDto.getPassword());
    }

    @RequestMapping(value = "/newPhoneNumber", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changePhoneNumber(@RequestBody UserDto userDto) {
        clientService.changePhoneNumber(userDto.getId(), userDto.getPassword());
    }

    @RequestMapping(value = "/newAddress", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeAddress(@RequestBody UserDto userDto) {
        clientService.changeAddress(userDto.getId(), userDto.getAddress());
    }

    @RequestMapping(value = "/allReviews", method = RequestMethod.GET)
    public List<Review> findAllReviews(@RequestBody ReviewDto reviewDto) throws ClientNotFoundException {
        UserDto userDto =adminService.findClientById(reviewDto.getUserId());
        List<Review>reviews=clientService.findAllReviews(userDto);
        return reviews;
    }

}
