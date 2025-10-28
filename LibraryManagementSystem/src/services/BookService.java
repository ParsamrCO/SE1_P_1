package services;

import models.Book;
import java.util.*;
import java.util.stream.Collectors;

public class BookService {
    private static BookService instance;
    private Map<String, Book> books;
    private int bookCounter;
    
    private BookService() {
        books = new HashMap<>();
        bookCounter = 1;
    }
    
    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }
    
    public boolean addBook(Book book, String employeeUsername) {
        if (books.containsKey(book.getBookId())) {
            return false;
        }
        books.put(book.getBookId(), book);
        return true;
    }
    
    public boolean updateBook(String bookId, Book updatedBook) {
        if (!books.containsKey(bookId)) {
            return false;
        }
        books.put(bookId, updatedBook);
        return true;
    }
    
    public List<Book> searchBooks(String title, String author, Integer publicationYear) {
        return books.values().stream()
                .filter(book -> title == null || book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> author == null || book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .filter(book -> publicationYear == null || book.getPublicationYear() == publicationYear)
                .collect(Collectors.toList());
    }
    
    public List<Book> searchBooksByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public Book findBookById(String bookId) {
        return books.get(bookId);
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    public int getTotalBooksCount() {
        return books.size();
    }
    
    public String generateBookId() {
        return "B" + (bookCounter++);
    }
}