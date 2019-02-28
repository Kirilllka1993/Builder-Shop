package com.vironit.kazimirov.entity.builder.Client;

import com.vironit.kazimirov.entity.Client;

public class ClientBuilder {
    private Client client;
    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder withId(int id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder withName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder withSurname(String surname) {
        client.setSurname(surname);
        return this;
    }

    public ClientBuilder withLogin(String login) {
        client.setLogin(login);
        return this;
    }


    public ClientBuilder withPassword(String password) {
        client.setPassword(password);
        return this;
    }

    public ClientBuilder withAdress(String adress) {
        client.setAddress(adress);
        return this;
    }

    public ClientBuilder withPhoneNumber(String phoneNumber) {
        client.setPhoneNumber(phoneNumber);
        return this;
    }

    public Client build() {
        return client;
    }
}
