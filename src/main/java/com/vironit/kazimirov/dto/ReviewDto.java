package com.vironit.kazimirov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto  {
    private int id;
    private String comment;
    private int mark;
    private int client;
    private int good;
}
