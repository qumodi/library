package com.example.library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
//@RequiredArgsConstructor
@Table(name = "checkouts")
@NoArgsConstructor
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @Column(name = "checkoutStart",columnDefinition = "Date")
    private LocalDate checkoutStart;
    @Column(name = "checkoutEnd",columnDefinition = "Date")
    private LocalDate checkoutEnd;

    @Column(name = "review")
    private String review;

    public Checkout(Book book,Visitor visitor,LocalDate checkoutStart,LocalDate checkoutEnd,String review){
        this.book = book;
        this.visitor = visitor;
        this.checkoutStart = checkoutStart;
        this.checkoutEnd = checkoutEnd;
        this.review = review;

        book.addCheckout(this);
        visitor.addCheckout(this);
    }
}
