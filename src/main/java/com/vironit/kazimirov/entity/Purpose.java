package com.vironit.kazimirov.entity;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "purpose")
public class Purpose {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="purpose")
    private String purpose;

    public Purpose(int id, String purpose) {
        this.id = id;
        this.purpose = purpose;
    }

    public Purpose() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purpose purpose1 = (Purpose) o;
        return id == purpose1.id &&
                Objects.equals(purpose, purpose1.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, purpose);
    }

    @Override
    public String toString() {
        return "Purpose{" +
                "id=" + id +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
