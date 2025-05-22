package com.example.librarysystem.controller;

import com.example.librarysystem.entity.Author;
import com.example.librarysystem.entity.Book;
import com.example.librarysystem.entity.User;
import com.example.librarysystem.repository.AuthorRepository;
import com.example.librarysystem.repository.BookRepository;
import com.example.librarysystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private Book testBook;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        userRepository.deleteAll();
        authorRepository.deleteAll();

        Author author = new Author();
        author.setFirstName("Jane");
        author.setLastName("Austen");
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Pride and Prejudice");
        book.setPublicationYear(1813);
        book.setAvailableCopies(5);
        book.setTotalCopies(5);
        book.setAuthor(savedAuthor);
        testBook = bookRepository.save(book);

        User user = new User();
        user.setFirstName("Anna");
        user.setLastName("Andersson");
        user.setEmail("anna@example.com");
        user.setPassword("securepassword");
        user.setRegistrationDate(LocalDate.now());
        testUser = userRepository.save(user);
    }

    @Test
    void testCreateLoan_Success() throws Exception {
        mockMvc.perform(post("/loans")
                        .param("userId", String.valueOf(testUser.getId()))
                        .param("bookId", String.valueOf(testBook.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(testUser.getId()))
                .andExpect(jsonPath("$.book.id").value(testBook.getId()))
                .andExpect(jsonPath("$.borrowedDate").exists())
                .andExpect(jsonPath("$.dueDate").exists());
    }
}
