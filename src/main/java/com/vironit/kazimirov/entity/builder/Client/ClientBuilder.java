package com.vironit.kazimirov.entity.builder.Client;

import com.vironit.kazimirov.entity.User;

public class ClientBuilder {
    private User user;
    public ClientBuilder() {
        user = new User();
    }

    public ClientBuilder withId(int id) {
        user.setId(id);
        return this;
    }

    public ClientBuilder withName(String name) {
        user.setName(name);
        return this;
    }

    public ClientBuilder withSurname(String surname) {
        user.setSurname(surname);
        return this;
    }

    public ClientBuilder withLogin(String login) {
        user.setLogin(login);
        return this;
    }


    public ClientBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public ClientBuilder withAdress(String adress) {
        user.setAddress(adress);
        return this;
    }

    public ClientBuilder withPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        return this;
    }

    public User build() {
        return user;
    }
}
