package com.vironit.kazimirov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;

}
