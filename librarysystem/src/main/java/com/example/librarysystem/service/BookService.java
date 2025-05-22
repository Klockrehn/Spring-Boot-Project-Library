package com.example.librarysystem.service;

import com.example.librarysystem.dto.BookDTO;
import com.example.librarysystem.dto.BookWithDetailsDTO;
import com.example.librarysystem.dto.AuthorDTO;
import com.example.librarysystem.entity.Book;
import com.example.librarysystem.entity.Author;
import com.example.librarysystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<BookWithDetailsDTO> getAllBooksWithDetails() {
        return bookRepository.findAll().stream()
                .map(this::mapToBookWithDetailsDTO)
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setTotalCopies(book.getTotalCopies());
        return dto;
    }

    public List<BookDTO> getAllBooksAsDTO() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookWithDetailsDTO mapToBookWithDetailsDTO(Book book) {
        Author author = book.getAuthor();
        AuthorDTO authorDTO = new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthYear(),
                author.getNationality()
        );

        return new BookWithDetailsDTO(
                book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getAvailableCopies(),
                book.getTotalCopies(),
                authorDTO
        );
    }
}
