package com.vironit.kazimirov.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "good")
public class Good {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "Subsection_id")
    private Subsection subsection;
    @Column(name = "unit")
    private String unit;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "discount")
    private double discount;
    @ManyToOne
    @JoinColumn(name = "Purpose_id")
    private Purpose purpose;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;

    public Good(int id, double price, Subsection subsection, String unit, int quantity, double discount, Purpose purpose, String name, int amount) {
        this.id = id;
        this.price = price;
        this.subsection = subsection;
        this.unit = unit;
        this.quantity = quantity;
        this.discount = discount;
        this.purpose = purpose;
        this.name = name;
        this.amount = amount;
    }

    public Good() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return id == good.id &&
                Double.compare(good.price, price) == 0 &&
                quantity == good.quantity &&
                amount == good.amount &&
                Objects.equals(subsection, good.subsection) &&
                Objects.equals(unit, good.unit) &&
                Objects.equals(discount, good.discount) &&
                Objects.equals(purpose, good.purpose) &&
                Objects.equals(name, good.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, subsection, unit, quantity, discount, purpose, name, amount);
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", price=" + price +
                ", subsection=" + subsection +
                ", unit='" + unit + '\'' +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", purpose=" + purpose +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
