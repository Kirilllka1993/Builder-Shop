package com.vironit.kazimirov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReviewClientDto {
    private int reviewId;
    private String comment;
    private int mark;
    private int clientId;
    private int goodId;
}
