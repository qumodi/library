package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> listBooks(String title){
        if (title != null) return bookRepository.findByTitle(title);
        return bookRepository.findAll();
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id){
        if (getBookById(id) != null) {
            bookRepository.deleteById(id);
        }
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElse(null);
    }
}
