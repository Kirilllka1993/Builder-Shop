package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Good;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodDto {
    private int id;
    private double price;
    private int subsectionId;
    private String unit;
    private int quantity;
    private double discount;
    private int purposeId;
    private String name;
    private int amount;

    public Good createGood(){
        Good good=new Good();
        good.setPrice(this.price);
        good.setUnit(this.unit);
        good.setQuantity(this.quantity);
        good.setName(this.name);
        good.setAmount(this.amount);
        return good;
    }

    public GoodDto(Good good){
        this.id=good.getId();
        this.price=good.getPrice();
        this.subsectionId=good.getSubsection().getId();
        this.purposeId=good.getPurpose().getId();
        this.unit=good.getUnit();
        this.quantity=good.getQuantity();
        this.discount=good.getDiscount();
        this.name=good.getName();
        this.amount=good.getAmount();
    }
}
