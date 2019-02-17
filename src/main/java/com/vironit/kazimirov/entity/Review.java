package com.vironit.kazimirov.entity;

import java.util.Objects;

public class Review {
    private int id;
    private String text;
    private int mark;
    private Client client;
    private Good good;

    public Review() {
    }

    public Review(int id, String text, int mark, Client client, Good good) {
        this.id = id;
        this.text = text;
        this.mark = mark;
        this.client = client;
        this.good = good;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id &&
                mark == review.mark &&
                Objects.equals(text, review.text) &&
                Objects.equals(client, review.client) &&
                Objects.equals(good, review.good);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, mark, client, good);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", mark=" + mark +
                ", client=" + client +
                ", good=" + good +
                '}';
    }
}
