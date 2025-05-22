package com.example.librarysystem.service;

import com.example.librarysystem.entity.Book;
import com.example.librarysystem.entity.Loan;
import com.example.librarysystem.entity.User;
import com.example.librarysystem.exception.BadRequestException;
import com.example.librarysystem.exception.ResourceNotFoundException;
import com.example.librarysystem.repository.BookRepository;
import com.example.librarysystem.repository.LoanRepository;
import com.example.librarysystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    public Optional<Loan> getLoanById(Long loanId) {
        return loanRepository.findById(loanId);
    }

    public Loan createLoan(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Bok med ID " + bookId + " hittades ej"));

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("Inga tillgängliga exemplar av boken");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Användare med ID " + userId + " hittades ej"));

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setBorrowedDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));

        return loanRepository.save(loan);
    }

    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Lån med ID " + loanId + " hittades ej"));

        if (loan.getReturnedDate() != null) {
            throw new BadRequestException("Boken är redan returnerad");
        }

        loan.setReturnedDate(LocalDate.now());

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    public Loan extendLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Lån med ID " + loanId + " hittades ej"));

        if (loan.getReturnedDate() != null) {
            throw new BadRequestException("Kan ej förlänga ett returnerat lån");
        }

        loan.setDueDate(loan.getDueDate().plusDays(14));

        return loanRepository.save(loan);
    }
}
