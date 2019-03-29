package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
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
}
