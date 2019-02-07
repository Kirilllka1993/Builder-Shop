package com.vironit.kazimirov.entity;

import java.util.Objects;

public class Good {
private int id;
private double cost;
private Subsection subsection;
private String unit;
private int quantity;
private Discount discount;
private Purpose purpose;
private String name;

    public Good(int id, double cost, Subsection subsection, String unit, int quantity, Discount discount, Purpose purpose, String name) {
        this.id = id;
        this.cost = cost; //стоимость товара
        this.subsection = subsection;//тип товара (утеплитель, сухие смеси и т. д.)
        this.unit = unit;// единица измерения
        this.quantity = quantity;//количество в упаковке
        this.discount = discount; //скидка на товар
        this.purpose = purpose; //назначение товара(для фундаментов)
        this.name = name;//наименование товара, марка
    }

    public Good() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Subsection getSubsection() {
        return subsection;
    }

    public void setSubsection(Subsection subsection) {
        this.subsection = subsection;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", cost=" + cost +
                ", subsection=" + subsection +
                ", unit='" + unit + '\'' +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", purpose=" + purpose +
                ", name='" + name + '\'' +
                '}';
    }
}
