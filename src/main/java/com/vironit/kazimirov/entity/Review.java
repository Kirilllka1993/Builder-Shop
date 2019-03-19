package com.vironit.kazimirov.entity;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "comment")
    private String comment;
    @Column(name = "mark")
    private int mark;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "Good_id")
    private Good good;

    public Review() {
    }

    public Review(int id, String text, int mark, Client client, Good good) {
        this.id = id;
        this.comment = text;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String text) {
        this.comment = text;
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
                Objects.equals(comment, review.comment) &&
                Objects.equals(client, review.client) &&
                Objects.equals(good, review.good);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, mark, client, good);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + comment + '\'' +
                ", mark=" + mark +
                ", client=" + client +
                ", good=" + good +
                '}';
    }
}
