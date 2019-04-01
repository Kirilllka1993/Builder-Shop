package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private int id;
    private int purchaseId;
    private int goodId;
    private int amount;
}
