package com.example.library.repositories;

import com.example.library.models.Book;
import com.example.library.models.Checkout;
import com.example.library.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {
    List<Checkout> findByBook(Book book);
    List<Checkout> findByVisitor(Visitor visitor);
}
