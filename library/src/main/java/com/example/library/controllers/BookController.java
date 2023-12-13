package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.models.Checkout;
import com.example.library.services.BookService;
import com.example.library.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CheckoutService checkoutService;

    @GetMapping("/books")
    public String books(Model model){
        model.addAttribute("books",bookService.listBooks(null));
        return "books";
    }

    @GetMapping("/book/add")
    public String addBook(Model model){
        return "book-add";
    }

    @PostMapping("/book/add")//
    public String addBookReal(@RequestParam String title,@RequestParam String author,@RequestParam int numberOfPages,@RequestParam String description, Model model){
        Book book = new Book(title,author,numberOfPages,description);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}")
    public String bookDetail(@PathVariable(value = "id") long id, Model model){
        if(bookService.getBookById(id) == null){
            return "redirect:/books";
        }

        Book book = bookService.getBookById(id);
        List<Checkout> checkoutsList = checkoutService.listCheckoutsByBook(book);

        model.addAttribute("book",book);
        model.addAttribute("checkouts",checkoutsList);
        return "book-info";
    }

    @GetMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable(value = "id") long id, Model model){
        if(bookService.getBookById(id) == null){
            return "redirect:/books";
        }

        Book book = bookService.getBookById(id);
        model.addAttribute("book",book);
        return "book-edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookUpdate(@PathVariable(value = "id") long id, @RequestParam String title,@RequestParam String author,@RequestParam int numberOfPages,@RequestParam String description){
        Book book = bookService.getBookById(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setNumberOfPages(numberOfPages);
        book.setDescription(description);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @PostMapping("/book/{id}/delete")
    public String bookDelete(@PathVariable(value = "id") long id,Model model){
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
