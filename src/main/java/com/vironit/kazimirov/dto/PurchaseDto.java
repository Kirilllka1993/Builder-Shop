package com.vironit.kazimirov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
    private int id;
    private double sum;
    private int client;
    private LocalDateTime registration;
    private LocalDateTime timeOfPurchase;
    private String status;
}
