package com.example.library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "visitors")
@NoArgsConstructor
@Data
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToMany(mappedBy = "visitor",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Checkout> checkouts = new ArrayList<Checkout>();

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phoneNumber")
    private String phoneNumber;

    public Visitor(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }
    public void addCheckout(Checkout checkout){
        checkouts.add(checkout);
    }
}
