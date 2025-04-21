package com.bookexchange.bookapp.controller;

import com.bookexchange.bookapp.model.Book;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class HomeController {

    // Shared book list
    public static final List<Book> books = new ArrayList<>(List.of(
        new Book() {{
            setId(1L); setTitle("Atomic Habits"); setAuthor("James Clear");
            setImageUrl("https://m.media-amazon.com/images/I/81F90H7hnML._AC_UF1000,1000_QL80_.jpg");
        }},
        new Book() {{
            setId(2L); setTitle("The Alchemist"); setAuthor("Paulo Coelho");
            setImageUrl("https://m.media-amazon.com/images/I/71aFt4+OTOL.jpg");
        }},
        new Book() {{
            setId(3L); setTitle("Clean Code"); setAuthor("Robert C. Martin");
            setImageUrl("https://m.media-amazon.com/images/I/41jEbK-jG+L._SX374_BO1,204,203,200_.jpg");
        }}
    ));
    

    // Incremental ID counter
    private long nextId = 4;

    @GetMapping("/home")
    public String homePage(Model model, HttpSession session) {
        model.addAttribute("books", books);
        return "home";
    }

    @PostMapping("/add-book")
    public String addCustomBook(
        @RequestParam String title,
        @RequestParam String author,
        @RequestParam("imageFile") MultipartFile imageFile
    ) {
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath();
        File file = new File(uploadDir, fileName);

        try {
            imageFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/home?error=true";
        }

        Book customBook = new Book();
        customBook.setId(nextId++); // Use incrementing ID
        customBook.setTitle(title);
        customBook.setAuthor(author);
        customBook.setImageUrl("/uploads/" + fileName);

        books.add(customBook);

        return "redirect:/home";
    }
    public List<Book> getBooks() {
        return books;
    }
    
}
