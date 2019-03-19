package com.vironit.kazimirov.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String address;
    private String phoneNumber;
}
