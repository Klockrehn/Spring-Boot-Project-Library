package com.example.librarysystem.service;

import com.example.librarysystem.dto.AuthorDTO;
import com.example.librarysystem.entity.Author;
import com.example.librarysystem.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> getAuthorsByLastName(String lastName) {
        return authorRepository.findByLastNameIgnoreCase(lastName);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        dto.setBirthYear(author.getBirthYear());
        dto.setNationality(author.getNationality());
        return dto;
    }

    public List<AuthorDTO> getAllAuthorsAsDTO() {
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
