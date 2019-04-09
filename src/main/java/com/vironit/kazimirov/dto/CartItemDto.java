package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.CartItem;
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

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.purchaseId = cartItem.getPurchase().getId();
        this.goodId = cartItem.getGood().getId();
        this.amount = cartItem.getAmount();
    }
}
