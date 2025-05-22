package com.example.librarysystem.controller;

import com.example.librarysystem.dto.AuthorDTO;
import com.example.librarysystem.entity.Author;
import com.example.librarysystem.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();

        return authors.stream()
                .map(authorService::convertToDTO)
                .collect(Collectors.toList());
    }
}
