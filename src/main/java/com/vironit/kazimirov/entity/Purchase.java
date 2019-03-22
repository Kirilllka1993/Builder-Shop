package com.vironit.kazimirov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "purchase")
public class Purchase {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "sum")
    private double sum;
    //(fetch = FetchType.EAGER)
//    @ManyToMany
//    @JoinTable(
//            name = "good_has_purchase",
//            joinColumns = @JoinColumn(name = "purchase_id"),
//            inverseJoinColumns = @JoinColumn(name = "good_id"))
//    private List<Good> goods;

    //(fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name = "Client_id")
    private Client client;
    @Column (name = "registration")
    private LocalDateTime registration;
    @Column (name = "timeOfPurchase")
    private LocalDateTime timeOfPurchase;
    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;


}
