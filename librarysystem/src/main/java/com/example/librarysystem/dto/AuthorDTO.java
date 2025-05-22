package com.example.librarysystem.dto;

public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer birthYear;
    private String nationality;

    public AuthorDTO() {}

    public AuthorDTO(Long id, String firstName, String lastName, Integer birthYear, String nationality) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.nationality = nationality;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}
