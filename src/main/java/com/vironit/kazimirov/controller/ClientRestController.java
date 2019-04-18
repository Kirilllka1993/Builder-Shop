package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.ReviewNotFoundException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "shop/client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/newReview", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int addReview(@RequestBody ReviewDto reviewDto) throws ClientNotFoundException, GoodNotFoundException {
        return clientService.addReview(reviewDto);
    }

    @RequestMapping(value = "/deleteReview", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteReview(@RequestBody ReviewDto reviewDto) throws GoodNotFoundException, ClientNotFoundException, ReviewNotFoundException {
        clientService.removeReview(reviewDto.getUserId(), reviewDto.getGoodId());
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public UserDto logIn(@RequestBody UserDto userDto) throws ClientNotFoundException {
        UserDto user = clientService.logIn(userDto.getLogin(), userDto.getPassword());
        return user;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int signIn(@RequestBody UserDto userDto) throws RepeatitionException {
        int clientId = clientService.signIn(userDto);
        return clientId;
    }

    @PreAuthorize("#userDto.id==authentication.principal.id or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/newLogin", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeLogin(@RequestBody UserDto userDto) throws RepeatitionException, ClientNotFoundException {
        clientService.changeLogin(userDto.getId(), userDto.getLogin());
    }


    @PreAuthorize("#userDto.id==authentication.principal.id or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/newPassword", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody UserDto userDto) throws ClientNotFoundException {
        clientService.changePassword(userDto.getId(), userDto.getPassword());
    }

    @PreAuthorize("#userDto.id==authentication.principal.id or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/newPhoneNumber", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changePhoneNumber(@RequestBody UserDto userDto) throws ClientNotFoundException {
        clientService.changePhoneNumber(userDto.getId(), userDto.getPhoneNumber());
    }

    @PreAuthorize("#userDto.id==authentication.principal.id or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/newAddress", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeAddress(@RequestBody UserDto userDto) throws ClientNotFoundException {
        clientService.changeAddress(userDto.getId(), userDto.getAddress());
    }

    @PreAuthorize("#userId==authentication.principal.id or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/allReviewsByUser/{userId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewDto> findAllReviewsByUser(@PathVariable ("userId")int userId) throws ClientNotFoundException {
        UserDto userDto = adminService.findClientById(userId);
        List<ReviewDto> reviewDtos = clientService.findAllReviewsByUser(userDto);
        return reviewDtos;
    }

    @RequestMapping(value = "/allReviews", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewDto> findAllReviews() {
        List<ReviewDto> reviewDtos = clientService.findAllReviews();
        return reviewDtos;
    }

    @RequestMapping(value = "/reviewById/{reviewId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto findAllReviews(@PathVariable("reviewId") int reviewId) throws ReviewNotFoundException {
        ReviewDto reviewDto = clientService.findReviewById(reviewId);
        return reviewDto;
    }
}
