package com.vironit.kazimirov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
