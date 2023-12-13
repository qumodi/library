package com.example.library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "numberOfPages")
    private int numberOfPages;
    @Column(name = "description",columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Checkout> checkouts = new ArrayList<Checkout>();

    public Book(String title, String author, int numberOfPages, String description) {
        this.title = title;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.description = description;
    }

    public void addCheckout(Checkout checkout){
        checkouts.add(checkout);
    }
}
