package com.vironit.kazimirov.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Purchase {
    private int id;
    private double cost;
    private List<Good> goods;
    private Client client;
    private LocalDateTime registration;
    private LocalDateTime purchase;
    private Status status;

    public Purchase(int id, double cost, List<Good> goods, Client client, LocalDateTime registration, LocalDateTime purchase, Status status) {
        this.id = id;
        this.cost = cost;
        this.goods = goods;
        this.client = client;
        this.registration = registration;
        this.purchase = purchase;
        this.status = status;
    }

    /*public Purchase(int id, double cost, List<Good> goods, Client client, LocalDateTime registration, LocalDateTime purchase, String status) {
        this.id = id;
        this.cost = cost;
        this.goods = goods;
        this.client = client;
        this.registration = registration;
        this.purchase = purchase;
        this.status = status;
    }*/

    public Purchase() {
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

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDateTime registration) {
        this.registration = registration;
    }

    public LocalDateTime getPurchase() {
        return purchase;
    }

    public void setPurchase(LocalDateTime purchase) {
        this.purchase = purchase;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase1 = (Purchase) o;
        return id == purchase1.id &&
                Double.compare(purchase1.cost, cost) == 0 &&
                Objects.equals(goods, purchase1.goods) &&
                Objects.equals(client, purchase1.client) &&
                Objects.equals(registration, purchase1.registration) &&
                Objects.equals(purchase, purchase1.purchase) &&
                status == purchase1.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, goods, client, registration, purchase, status);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", cost=" + cost +
                ", goods=" + goods +
                ", client=" + client +
                ", registration=" + registration +
                ", purchase=" + purchase +
                ", status=" + status +
                '}';
    }
}
