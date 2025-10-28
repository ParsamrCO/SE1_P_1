package models;

import java.time.LocalDate;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private boolean available;
    private LocalDate registrationDate;
    private String registeredBy;
    
    public Book(String bookId, String title, String author, int publicationYear, 
                String isbn, boolean available, String registeredBy) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.available = available;
        this.registeredBy = registeredBy;
        this.registrationDate = LocalDate.now();
    }
    
    // Getters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return available; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public String getRegisteredBy() { return registeredBy; }
    
    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAvailable(boolean available) { this.available = available; }
    
    @Override
    public String toString() {
        return String.format("ID: %s, Title: %s, Author: %s, Year: %d, Available: %s",
                bookId, title, author, publicationYear, available ? "Yes" : "No");
    }
}