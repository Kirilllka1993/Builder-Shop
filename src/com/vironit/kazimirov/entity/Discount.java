package com.vironit.kazimirov.entity;

import java.util.Objects;

public class Discount {
    private int discount;

    public Discount(int discount) {
        this.discount = discount;
    }

    public Discount() {
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount1 = (Discount) o;
        return discount == discount1.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discount);
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discount=" + discount +
                '}';
    }
}
