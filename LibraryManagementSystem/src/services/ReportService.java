package services;

import models.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
    private static ReportService instance;
    
    private ReportService() {}
    
    public static ReportService getInstance() {
        if (instance == null) {
            instance = new ReportService();
        }
        return instance;
    }
    
    public static class StudentStatistics {
        public int totalBorrows;
        public int notReturnedCount;
        public int delayedReturns;
        public List<BorrowRecord> borrowHistory;
        
        public StudentStatistics(int totalBorrows, int notReturnedCount, int delayedReturns, 
                               List<BorrowRecord> borrowHistory) {
            this.totalBorrows = totalBorrows;
            this.notReturnedCount = notReturnedCount;
            this.delayedReturns = delayedReturns;
            this.borrowHistory = borrowHistory;
        }
    }
    
    public static class EmployeePerformance {
        public int booksRegistered;
        public int booksLent;
        public int booksReceived;
        
        public EmployeePerformance(int booksRegistered, int booksLent, int booksReceived) {
            this.booksRegistered = booksRegistered;
            this.booksLent = booksLent;
            this.booksReceived = booksReceived;
        }
    }
    
    public static class LibraryStatistics {
        public int totalStudents;
        public int totalBooks;
        public int totalBorrows;
        public int activeBorrows;
        
        public LibraryStatistics(int totalStudents, int totalBooks, int totalBorrows, int activeBorrows) {
            this.totalStudents = totalStudents;
            this.totalBooks = totalBooks;
            this.totalBorrows = totalBorrows;
            this.activeBorrows = activeBorrows;
        }
    }
    
    public static class BorrowStatistics {
        public int totalRequests;
        public int totalBorrows;
        public double averageBorrowDays;
        
        public BorrowStatistics(int totalRequests, int totalBorrows, double averageBorrowDays) {
            this.totalRequests = totalRequests;
            this.totalBorrows = totalBorrows;
            this.averageBorrowDays = averageBorrowDays;
        }
    }
    
    public static class StudentDelayStats {
        public String username;
        public String fullName;
        public int delayCount;
        
        public StudentDelayStats(String username, String fullName, int delayCount) {
            this.username = username;
            this.fullName = fullName;
            this.delayCount = delayCount;
        }
    }
    
    // Student reports
    public StudentStatistics getStudentStatistics(String studentUsername) {
        UserService userService = UserService.getInstance();
        BorrowService borrowService = BorrowService.getInstance();
        
        Student student = userService.getStudent(studentUsername);
        if (student == null) {
            return null;
        }
        
        List<BorrowRecord> history = borrowService.getStudentBorrowHistory(studentUsername);
        
        return new StudentStatistics(
            student.getTotalBorrows(),
            student.getNotReturnedCount(),
            student.getDelayedReturns(),
            history
        );
    }
    
    // Employee performance reports
    public EmployeePerformance getEmployeePerformance(String employeeUsername) {
        UserService userService = UserService.getInstance();
        Employee employee = userService.getEmployee(employeeUsername);
        
        if (employee == null) {
            return null;
        }
        
        return new EmployeePerformance(
            employee.getBooksRegistered(),
            employee.getBooksLent(),
            employee.getBooksReceived()
        );
    }
    
    // Library statistics
    public LibraryStatistics getLibraryStatistics() {
        UserService userService = UserService.getInstance();
        BookService bookService = BookService.getInstance();
        BorrowService borrowService = BorrowService.getInstance();
        
        return new LibraryStatistics(
            userService.getTotalStudentsCount(),
            bookService.getTotalBooksCount(),
            borrowService.getTotalBorrowsCount(),
            borrowService.getActiveBorrowsCount()
        );
    }
    
    // Borrow statistics
    public BorrowStatistics getBorrowStatistics() {
        BorrowService borrowService = BorrowService.getInstance();
        List<BorrowRecord> records = borrowService.getAllBorrowRecords();
        
        int totalRequests = records.size();
        int totalBorrows = records.size();
        
        double averageDays = records.stream()
                .filter(BorrowRecord::isReturned)
                .mapToLong(record -> java.time.temporal.ChronoUnit.DAYS.between(
                    record.getBorrowDate(), record.getReturnDate()))
                .average()
                .orElse(0.0);
        
        return new BorrowStatistics(totalRequests, totalBorrows, averageDays);
    }
    
    // Top 10 students with most delays
    public List<StudentDelayStats> getTopStudentsWithDelays() {
        UserService userService = UserService.getInstance();
        
        return userService.getAllStudents().stream()
                .filter(student -> student.getDelayedReturns() > 0)
                .sorted((s1, s2) -> Integer.compare(s2.getDelayedReturns(), s1.getDelayedReturns()))
                .limit(10)
                .map(student -> new StudentDelayStats(
                    student.getUsername(), 
                    student.getFullName(), 
                    student.getDelayedReturns()))
                .collect(Collectors.toList());
    }
}