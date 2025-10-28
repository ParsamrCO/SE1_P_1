package controllers;

import models.*;
import services.*;
import java.time.LocalDate;
import java.util.List;

public class StudentController {
    private Student currentStudent;
    private BookService bookService;
    private BorrowService borrowService;
    private ReportService reportService;
    
    public StudentController() {
        this.bookService = BookService.getInstance();
        this.borrowService = BorrowService.getInstance();
        this.reportService = ReportService.getInstance();
    }
    
    public boolean login(String username, String password) {
        UserService userService = UserService.getInstance();
        Student student = userService.loginStudent(username, password);
        if (student != null) {
            this.currentStudent = student;
            return true;
        }
        return false;
    }
    
    public boolean register(String username, String password, String fullName, String studentId) {
        UserService userService = UserService.getInstance();
        return userService.registerStudent(username, password, fullName, studentId);
    }
    
    public List<Book> searchBooks(String title, String author, Integer year) {
        return bookService.searchBooks(title, author, year);
    }
    
    public boolean requestBookBorrow(String bookId, LocalDate startDate, LocalDate endDate) {
        if (currentStudent == null) {
            return false;
        }
        return borrowService.requestBorrow(currentStudent.getUsername(), bookId, startDate, endDate);
    }
    
    public ReportService.StudentStatistics getStudentStatistics() {
        if (currentStudent == null) {
            return null;
        }
        return reportService.getStudentStatistics(currentStudent.getUsername());
    }
    
    public List<BorrowRecord> getBorrowHistory() {
        if (currentStudent == null) {
            return null;
        }
        return borrowService.getStudentBorrowHistory(currentStudent.getUsername());
    }
    
    public Student getCurrentStudent() {
        return currentStudent;
    }
    
    public void logout() {
        this.currentStudent = null;
    }
}