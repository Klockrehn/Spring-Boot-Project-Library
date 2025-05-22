package com.example.librarysystem.dto;

public class BookWithDetailsDTO {
    private Long id;
    private String title;
    private Integer publicationYear;
    private Integer availableCopies;
    private Integer totalCopies;
    private AuthorDTO author;

    public BookWithDetailsDTO() {}

    public BookWithDetailsDTO(Long id, String title, Integer publicationYear,
                              Integer availableCopies, Integer totalCopies,
                              AuthorDTO author) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.author = author;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public Integer getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }

    public Integer getTotalCopies() { return totalCopies; }
    public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }

    public AuthorDTO getAuthor() { return author; }
    public void setAuthor(AuthorDTO author) { this.author = author; }
}

