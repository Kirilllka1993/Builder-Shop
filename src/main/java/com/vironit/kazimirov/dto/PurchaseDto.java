package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Status;
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
    private Client client;
    private LocalDateTime registration;
    private LocalDateTime timeOfPurchase;
    private Status status;
}
