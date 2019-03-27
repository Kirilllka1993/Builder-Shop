package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "client")
public class ClientRestController {
    @Autowired
    private ClientService clientService;


}
