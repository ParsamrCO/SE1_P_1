package controllers;

import models.*;
import services.*;
import java.util.List;

public class EmployeeController {
    private Employee currentEmployee;
    private BookService bookService;
    private BorrowService borrowService;
    private UserService userService;
    private ReportService reportService;
    
    public EmployeeController() {
        this.bookService = BookService.getInstance();
        this.borrowService = BorrowService.getInstance();
        this.userService = UserService.getInstance();
        this.reportService = ReportService.getInstance();
    }
    
    public boolean login(String username, String password) {
        Employee employee = userService.loginEmployee(username, password);
        if (employee != null) {
            this.currentEmployee = employee;
            return true;
        }
        return false;
    }
    
    public boolean changePassword(String newPassword) {
        if (currentEmployee != null) {
            currentEmployee.setPassword(newPassword);
            return true;
        }
        return false;
    }
    
    public boolean addBook(Book book) {
        boolean success = bookService.addBook(book, currentEmployee.getUsername());
        if (success) {
            currentEmployee.incrementBooksRegistered();
        }
        return success;
    }
    
    public boolean updateBook(String bookId, Book updatedBook) {
        return bookService.updateBook(bookId, updatedBook);
    }
    
    public List<BorrowRequest> getTodayPendingRequests() {
        return borrowService.getPendingRequestsForToday();
    }
    
    public boolean processBorrowRequest(String requestId) {
        return borrowService.approveBorrowRequest(requestId, currentEmployee.getUsername());
    }
    
    public boolean receiveReturnedBook(String recordId) {
        return borrowService.returnBook(recordId, currentEmployee.getUsername());
    }
    
    public boolean toggleStudentStatus(String studentUsername) {
        return userService.toggleStudentStatus(studentUsername);
    }
    
    public ReportService.StudentStatistics getStudentStatistics(String studentUsername) {
        return reportService.getStudentStatistics(studentUsername);
    }
    
    public Employee getCurrentEmployee() {
        return currentEmployee;
    }
    
    public void logout() {
        this.currentEmployee = null;
    }
}