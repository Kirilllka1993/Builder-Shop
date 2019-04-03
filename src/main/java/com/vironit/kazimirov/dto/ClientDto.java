package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.UserRoleEnum;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String address;
    private String phoneNumber;
    private String userRoleEnum;

    public User createClient(){
        User user =new User();
        user.setLogin(this.login);
        user.setAddress(this.address);
        user.setPassword(this.password);
        user.setPhoneNumber(this.phoneNumber);
        user.setSurname(this.surname);
        user.setName(this.name);
        user.setUserRoleEnum(UserRoleEnum.valueOf(this.userRoleEnum));
        return user;
    }
}
