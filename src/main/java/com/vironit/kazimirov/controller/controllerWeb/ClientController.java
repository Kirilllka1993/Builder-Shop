package com.vironit.kazimirov.controller.controllerWeb;


import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.ReviewNotFoundException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.ClientService;
import com.vironit.kazimirov.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import java.util.List;

@Controller
public class ClientController extends HttpServlet {
    @Autowired
    private ClientService clientService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/clientPage", method = RequestMethod.GET)
    public ModelAndView moveToGoodPage(ModelMap map) {
        List<GoodDto> goods = goodService.findAllGoods();
        map.addAttribute("goods", goods);
        List<UserDto> users = adminService.findAllClient();
        map.addAttribute("clients", users);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(map);
        modelAndView.setViewName("client");
        return modelAndView;
    }

    @RequestMapping(value = "/addReview", method = RequestMethod.POST)
    public ModelAndView addReview(@RequestParam("mark") int mark,
                                  @RequestParam("user") String login,
                                  @RequestParam("goodId") String name,
                                  @RequestParam("comment") String comment,
                                  ModelMap map) throws ClientNotFoundException, GoodNotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
//        UserDto userDto = adminService.findClientByLogin(login);
//        GoodDto goodDto = goodService.findByNameGood(name);
//        Review review = new Review();
//        review.setMark(mark);
//        review.setComment(comment);
//        review.setGood(goodDto);
//        review.setUser(user);
//        clientService.addReview(review);
        return modelAndView;
    }

    @RequestMapping(value = "/removeReview", method = RequestMethod.DELETE)
    public void removeReview(int id, User user) {

    }

    @RequestMapping(value = "/logIn", method = RequestMethod.GET)
    public ModelAndView logIn(@RequestParam("login") String login, @RequestParam("password") String password) throws ClientNotFoundException {

        ModelAndView modelAndView = new ModelAndView();
//        User user =null;
//        try{
//             user= clientService.logIn(login, password);
//            return modelAndView;
//        }catch(ClientNotFoundException e){
//
//        }
        UserDto userDto = clientService.logIn(login, password);
        if (userDto == null) {
            throw new ClientNotFoundException();
        } else {
            modelAndView.setViewName("user");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public void logOut() {

    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public String signIn(UserDto userDto, ModelMap map){
        map.addAttribute("command", userDto);
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPassword(userDto.getPassword());
        user.setLogin(userDto.getLogin());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        try {
            clientService.signIn(userDto);
        } catch (RepeatitionException e) {
            return "tryLogin";
        }

        return "Congratulate! You Add!";
    }

    @RequestMapping(value = "/changeLogin", method = RequestMethod.POST)
    public ModelAndView changeLogin(@RequestParam("id") int id,
                                    @RequestParam("login") String login, ModelMap map){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        try {
            clientService.changeLogin(id, login);
        } catch (RepeatitionException e) {
            modelAndView.setViewName("tryLogin");
            return modelAndView;
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public void changePassword(int id, String password) {

    }

    @RequestMapping(value = "/changePhoneNumber", method = RequestMethod.POST)
    public void changePhoneNumber(@RequestParam("id") int id,
                                  @RequestParam("phoneNumber") String phoneNumber, ModelMap map) {
    }

    @RequestMapping(value = "/changeAddress", method = RequestMethod.POST)
    public ModelAndView changeAddress(@RequestParam("id") int id,
                                      @RequestParam("address") String address, ModelMap map) throws ClientNotFoundException {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        clientService.changeAddress(id, address);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteReview", method = RequestMethod.POST)
    public ModelAndView deleteReview(@RequestParam("idGood") int idGood,
                                     @RequestParam("idClient") int idClient, ModelMap map) throws GoodNotFoundException, ClientNotFoundException, ReviewNotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        clientService.removeReview(idClient, idGood);
        return modelAndView;
    }
}
