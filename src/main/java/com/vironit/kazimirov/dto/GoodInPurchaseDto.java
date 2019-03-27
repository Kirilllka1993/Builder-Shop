package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodInPurchaseDto {
    private int id;
    private Purchase purchase;
    private Good good;
    private int amount;
}