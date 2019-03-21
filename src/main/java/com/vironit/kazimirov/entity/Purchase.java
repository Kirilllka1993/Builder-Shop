package com.vironit.kazimirov.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "summa")
    private double summa;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "good_has_purchase",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "good_id"))
    private List<Good> goods;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Client_id")
    private Client client;
    @Column (name = "registration")
    private LocalDateTime registration;
    @Column (name = "timeOfPurchase")
    private LocalDateTime timeOfPurchase;
    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public Purchase(int id, double summa, List<Good> goods, Client client, LocalDateTime registration, LocalDateTime timeOfPurchase, Status status) {
        this.id = id;
        this.summa = summa;
        this.goods = goods;
        this.client = client;
        this.registration = registration;
        this.timeOfPurchase = timeOfPurchase;
        this.status = status;
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

    public void setSumma(double cost) {
        this.summa = cost;
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

    public LocalDateTime getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public void setTimeOfPurchase(LocalDateTime purchase) {
        this.timeOfPurchase = purchase;
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
                Double.compare(purchase1.summa, summa) == 0 &&
                Objects.equals(goods, purchase1.goods) &&
                Objects.equals(client, purchase1.client) &&
                Objects.equals(registration, purchase1.registration) &&
                Objects.equals(timeOfPurchase, purchase1.timeOfPurchase) &&
                status == purchase1.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, summa, goods, client, registration, timeOfPurchase, status);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", cost=" + summa +
                ", goods=" + goods +
                ", client=" + client +
                ", registration=" + registration +
                ", purchase=" + timeOfPurchase +
                ", status=" + status +
                '}';
    }
}
