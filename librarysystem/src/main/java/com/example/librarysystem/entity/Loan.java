package com.example.librarysystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrowed_date")
    private LocalDate borrowedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "returned_date")
    private LocalDate returnedDate;

    public Loan() {}

    public Loan(User user, Book book, LocalDate borrowedDate, LocalDate dueDate) {
        this.user = user;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public LocalDate getBorrowedDate() { return borrowedDate; }
    public void setBorrowedDate(LocalDate borrowedDate) { this.borrowedDate = borrowedDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate returnedDate) { this.returnedDate = returnedDate; }
}

