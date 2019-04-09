package com.vironit.kazimirov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @ManyToOne
    @JoinColumn(name = "Client_id")
    private User user;
    @Column (name = "registration")
    private LocalDateTime registration;
    @Column (name = "timeOfPurchase")
    private LocalDateTime timeOfPurchase;
    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;


}
