package controllers;

import models.Book;
import services.*;
import java.util.List;

public class GuestController {
    private UserService userService;
    private BookService bookService;
    private ReportService reportService;
    
    public GuestController() {
        this.userService = UserService.getInstance();
        this.bookService = BookService.getInstance();
        this.reportService = ReportService.getInstance();
    }
    
    public int getRegisteredStudentsCount() {
        return userService.getTotalStudentsCount();
    }
    
    public List<Book> searchBooksByTitle(String title) {
        return bookService.searchBooksByTitle(title);
    }
    
    public ReportService.LibraryStatistics getSimpleStatistics() {
        return reportService.getLibraryStatistics();
    }
}