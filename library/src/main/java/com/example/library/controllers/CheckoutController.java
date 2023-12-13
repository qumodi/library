package com.example.library.controllers;

import com.example.library.models.Checkout;
import com.example.library.services.BookService;
import com.example.library.services.CheckoutService;
import com.example.library.services.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final BookService bookService;
    private final VisitorService visitorService;

    @GetMapping("checkouts")
    public String checkouts(Model model){
        model.addAttribute("checkouts",checkoutService.listCheckoutsByVisitor(null));
        return "checkouts";
    }

    @GetMapping("checkout/add")
    public String addCheckout(Model model){
        return "checkout-add";
    }

    @PostMapping("/checkout/add")
    public String addCheckoutReal(@RequestParam Long book,
                                  @RequestParam Long visitor,
                                  @RequestParam String checkoutStart,
                                  @RequestParam String checkoutEnd,
                                  @RequestParam String review,
                                  Model model){
        DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate start = LocalDate.parse(checkoutStart, FORMAT);
        LocalDate end = LocalDate.parse(checkoutEnd, FORMAT);

        if(bookService.getBookById(book) != null && visitorService.getVisitorByID(visitor) != null){
            Checkout checkout = new Checkout(bookService.getBookById(book),visitorService.getVisitorByID(visitor),
                    start,end,review);
//                    (checkoutStart),checkoutEnd,review);
            checkoutService.saveCheckout(checkout);
        }
        return "redirect:/checkouts";
    }

    @GetMapping("/checkout/{id}")
    public String checkoutDetail(@PathVariable(value="id") long id,Model model){
        if(checkoutService.getCheckoutById(id)==null){
            return "redirect:/checkouts";
        }
        Checkout checkout = checkoutService.getCheckoutById(id);
        model.addAttribute("checkout",checkout);
        return "checkout-info";
    }

    @GetMapping("/checkout/{id}/edit")
    public String checkoutEdit(@PathVariable(value = "id") long id, Model model){
        if(checkoutService.getCheckoutById(id)==null){
            return "redirect:/checkouts";
        }
        Checkout checkout = checkoutService.getCheckoutById(id);
        model.addAttribute("checkout",checkout);
        return "checkout-edit";
    }

    @PostMapping("/checkout/{id}/edit")
    public String checkoutUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam Long bookId,
                                 @RequestParam Long visitorId,
                                 @RequestParam String checkoutStart,
                                 @RequestParam String checkoutEnd,
                                 @RequestParam String review){

        DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate start = LocalDate.parse(checkoutStart, FORMAT);
        LocalDate end = LocalDate.parse(checkoutEnd, FORMAT);

        if(bookService.getBookById(bookId) == null || visitorService.getVisitorByID(visitorId) == null){
            return "redicect:/checkouts";
        }

        if(bookId != checkoutService.getCheckoutById(id).getBook().getId()) {
            bookService.getBookById(bookId).getCheckouts().remove(checkoutService.getCheckoutById(id));
        }
        if(visitorId != checkoutService.getCheckoutById(id).getVisitor().getId()) {
            visitorService.getVisitorByID(visitorId).getCheckouts().remove(checkoutService.getCheckoutById(id));
        }

        Checkout checkout = checkoutService.getCheckoutById(id);
        checkout.setBook(bookService.getBookById(bookId));
        checkout.setVisitor(visitorService.getVisitorByID(visitorId));
        checkout.setCheckoutStart(start);
        checkout.setCheckoutEnd(end);
        checkout.setReview(review);
        return "redirect:/checkouts";
    }

    @PostMapping("/checkout/{id}/delete")
    public String checkoutDelete(@PathVariable(value = "id") long id,Model model){

        checkoutService.deleteCheckout(id);
        return "redirect:/checkouts";
    }
}
