package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoodDto {
    private int id;
    private double price;
    private Subsection subsection;
    private String unit;
    private int quantity;
    private double discount;
    private Purpose purpose;
    private String name;
    private int amount;

    public Good createGood(){
        Good good=new Good();
        good.setPrice(this.price);
        good.setSubsection(this.subsection);
        good.setUnit(this.unit);
        good.setQuantity(this.quantity);
        good.setPurpose(this.purpose);
        good.setName(this.name);
        good.setAmount(this.amount);
        return good;
    }
}
