package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Purchase;
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
    private int userId;
    private LocalDateTime registration;
    private LocalDateTime timeOfPurchase;
    private String status;

    public PurchaseDto(Purchase purchase){
        this.id=purchase.getId();
        this.sum=purchase.getSum();
        this.userId =purchase.getUser().getId();
        this.registration=purchase.getRegistration();
        this.timeOfPurchase=purchase.getTimeOfPurchase();
        this.status=purchase.getStatus().name();
    }
}
