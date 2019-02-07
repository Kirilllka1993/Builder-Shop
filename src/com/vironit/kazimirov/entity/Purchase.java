package com.vironit.kazimirov.entity;

import java.util.Objects;

public class Purchase {
    private int id;
    private double summa;

    public Purchase(int id, double summa) {
        this.id = id;
        this.summa = summa;
    }

    public Purchase() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return id == purchase.id &&
                Double.compare(purchase.summa, summa) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, summa);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", summa=" + summa +
                '}';
    }
}
