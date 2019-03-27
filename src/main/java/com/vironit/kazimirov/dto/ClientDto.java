package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Client;
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

    public Client createClient(){
        Client client=new Client();
        client.setLogin(this.login);
        client.setAddress(this.address);
        client.setPassword(this.password);
        client.setPhoneNumber(this.phoneNumber);
        client.setSurname(this.surname);
        client.setName(this.name);
        return client;
    }
}
