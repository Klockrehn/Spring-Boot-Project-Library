package com.example.librarysystem.service;

import com.example.librarysystem.entity.Book;
import com.example.librarysystem.entity.Loan;
import com.example.librarysystem.entity.User;
import com.example.librarysystem.exception.BadRequestException;
import com.example.librarysystem.exception.ResourceNotFoundException;
import com.example.librarysystem.repository.BookRepository;
import com.example.librarysystem.repository.LoanRepository;
import com.example.librarysystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanRepository = mock(LoanRepository.class);
        bookRepository = mock(BookRepository.class);
        userRepository = mock(UserRepository.class);
        loanService = new LoanService(loanRepository, bookRepository, userRepository);
    }

    @Test
    void createLoan_shouldCreateLoanSuccessfully() {
        Long userId = 1L;
        Long bookId = 10L;

        Book book = new Book();
        book.setAvailableCopies(2);

        User user = new User();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanRepository.save(any(Loan.class))).thenAnswer(i -> i.getArgument(0));

        Loan result = loanService.createLoan(userId, bookId);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(book, result.getBook());
        assertEquals(LocalDate.now(), result.getBorrowedDate());
        assertEquals(LocalDate.now().plusDays(14), result.getDueDate());
        assertNull(result.getReturnedDate());

        verify(bookRepository).save(book);
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void createLoan_shouldThrowException_whenNoAvailableCopies() {
        Long userId = 1L;
        Long bookId = 10L;

        Book book = new Book();
        book.setAvailableCopies(0);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Exception ex = assertThrows(BadRequestException.class, () -> {
            loanService.createLoan(userId, bookId);
        });

        assertEquals("Inga tillgängliga exemplar av boken", ex.getMessage());
    }

    @Test
    void createLoan_shouldThrowException_whenBookNotFound() {
        when(bookRepository.findById(10L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            loanService.createLoan(1L, 10L);
        });

        assertEquals("Bok med ID 10 hittades ej", ex.getMessage());
    }

    @Test
    void createLoan_shouldThrowException_whenUserNotFound() {
        Book book = new Book();
        book.setAvailableCopies(1);

        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            loanService.createLoan(1L, 10L);
        });

        assertEquals("Användare med ID 1 hittades ej", ex.getMessage());
    }
}

