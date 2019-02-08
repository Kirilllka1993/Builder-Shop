package com.vironit.kazimirov.entity.builder.Good;

import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;

public class GoodBuilder {
    private Good good;

    public GoodBuilder() {
        good = new Good();
    }

    public GoodBuilder withCost(double price) {
        good.setPrice(price);
        return this;
    }


    public GoodBuilder withSubsection(Subsection subsection) {
        good.setSubsection(subsection);
        return this;
    }

    public GoodBuilder withUnit(String unit) {
        good.setUnit(unit);
        return this;
    }

    public GoodBuilder withQuantity(int quantity) {
        good.setQuantity(quantity);
        return this;
    }

    public GoodBuilder withDiscount(Discount discount) {
        good.setDiscount(discount);
        return this;
    }

    public GoodBuilder withPurpose(Purpose purpose) {
        good.setPurpose(purpose);
        return this;
    }

    public GoodBuilder withName(String name) {
        good.setName(name);
        return this;
    }
    public GoodBuilder withAmount(int amount) {
        good.setAmount(amount);
        return this;
    }

    public Good build() {
        return good;
    }

}
