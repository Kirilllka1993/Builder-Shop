package com.vironit.kazimirov.controller.controllerWeb;


import com.vironit.kazimirov.dto.ClientDto;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.ClientService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        List<Good> goods = goodService.findAllGoods();
        map.addAttribute("goods", goods);
        List<Client> clients = adminService.findAllClient();
        map.addAttribute("clients", clients);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(map);
        modelAndView.setViewName("client");
        return modelAndView;
    }

    @RequestMapping(value = "/addReview", method = RequestMethod.POST)
    public ModelAndView addReview(@RequestParam("mark") int mark,
                                  @RequestParam("client") String login,
                                  @RequestParam("good") String name,
                                  @RequestParam("comment") String comment,
                                  ModelMap map) throws ClientNotFoundException, GoodNotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("client");
        Client client = adminService.findClientByLogin(login);
        Good good1 = goodService.findByNameGood(name);
        Review review = new Review();
        review.setMark(mark);
        review.setComment(comment);
        review.setGood(good1);
        review.setClient(client);
        clientService.addReview(review);
        return modelAndView;
    }

    @RequestMapping(value = "/removeReview", method = RequestMethod.DELETE)
    public void removeReview(int id, Client client) {

    }

    @RequestMapping(value = "/logIn", method = RequestMethod.GET)
    public ModelAndView logIn(@RequestParam("login") String login, @RequestParam("password") String password) throws ClientNotFoundException {

        ModelAndView modelAndView = new ModelAndView();
//        Client client =null;
//        try{
//             client= clientService.logIn(login, password);
//            return modelAndView;
//        }catch(ClientNotFoundException e){
//
//        }
        Client client= clientService.logIn(login, password);
        if (client == null) {
            throw new ClientNotFoundException();
        } else {
            modelAndView.setViewName("client");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public void logOut() {

    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public String signIn(ClientDto clientDto, ModelMap map){
        map.addAttribute("command", clientDto);
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setSurname(clientDto.getSurname());
        client.setPassword(clientDto.getPassword());
        client.setLogin(clientDto.getLogin());
        client.setAddress(clientDto.getAddress());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        try {
            clientService.signIn(client);
        } catch (RepeatitionException e) {
            return "tryLogin";
        }

        return "Congratulate! You Add!";
    }

    @RequestMapping(value = "/changeLogin", method = RequestMethod.POST)
    public ModelAndView changeLogin(@RequestParam("id") int id,
                                    @RequestParam("login") String login, ModelMap map){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("client");
        try {
            clientService.changeLogin(id, login);
        } catch (RepeatitionException e) {
            modelAndView.setViewName("tryLogin");
            return modelAndView;
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
                                      @RequestParam("address") String address, ModelMap map) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("client");
        clientService.changeAddress(id, address);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteReview", method = RequestMethod.POST)
    public ModelAndView deleteReview(@RequestParam("idGood") int idGood,
                                     @RequestParam("idClient") int idClient, ModelMap map) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("client");
        clientService.removeReview(idClient, idGood);
        return modelAndView;
    }
}
