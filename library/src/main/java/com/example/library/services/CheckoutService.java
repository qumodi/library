package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.Checkout;
import com.example.library.models.Visitor;
import com.example.library.repositories.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CheckoutRepository checkoutRepository;

    public List<Checkout> listCheckoutsByBook(Book book){
        if(book != null) return checkoutRepository.findByBook(book);
        return null;
    }

    public  List<Checkout> listCheckoutsByVisitor(Visitor visitor){
        if(visitor != null) return checkoutRepository.findByVisitor(visitor);
        return checkoutRepository.findAll();
    }
    public void saveCheckout(Checkout checkout){
        checkoutRepository.save(checkout);
    }

    public void deleteCheckout(Long id){
        if(getCheckoutById(id) != null){
            checkoutRepository.deleteById(id);
        }
    }

    public Checkout getCheckoutById(Long id){
        return checkoutRepository.findById(id).orElse(null);
    }

}
