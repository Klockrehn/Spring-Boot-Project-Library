package com.example.librarysystem.controller;

import com.example.librarysystem.dto.BookWithDetailsDTO;
import com.example.librarysystem.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookWithDetailsDTO> getAllBooksWithDetails() {
        return bookService.getAllBooksWithDetails();
    }
}
