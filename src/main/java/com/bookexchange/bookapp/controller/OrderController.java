package com.bookexchange.bookapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookexchange.bookapp.model.Book;



@Controller
public class OrderController {

    @GetMapping("/order")
    public String orderPage(
        @RequestParam Long bookId,
        @RequestParam String action,
        Model model) {

        Book book = HomeController.books.stream()
            .filter(b -> b.getId().equals(bookId))
            .findFirst().orElse(null);

        if (book == null) {
            return "redirect:/home?error=bookNotFound";
        }

        model.addAttribute("book", book);
        model.addAttribute("action", action);
        return "order";
    }

    @PostMapping("/payment")
    public String paymentPage(@RequestParam Long bookId,
                              @RequestParam String action,
                              Model model) {
        model.addAttribute("bookId", bookId);
        model.addAttribute("action", action);
        return "payment";
    }

    @PostMapping("/payment-done")
    public String paymentDone(@RequestParam Long bookId,
                              @RequestParam String action,
                              Model model) {
        Book book = HomeController.books.stream()
            .filter(b -> b.getId().equals(bookId))
            .findFirst().orElse(null);

        model.addAttribute("book", book);
        model.addAttribute("action", action);
        model.addAttribute("success", true);
        return "order"; // or show a success page
    }
    @GetMapping("/payment-success")
public String paymentSuccess() {
    return "payment-success"; // returns payment-success.html
}

}
