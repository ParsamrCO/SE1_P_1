package services;

import models.*;
import java.util.*;
import java.util.stream.Collectors;

public class BorrowService {
    private static BorrowService instance;
    private Map<String, BorrowRequest> borrowRequests;
    private Map<String, BorrowRecord> borrowRecords;
    private int requestCounter;
    private int recordCounter;
    
    private BorrowService() {
        borrowRequests = new HashMap<>();
        borrowRecords = new HashMap<>();
        requestCounter = 1;
        recordCounter = 1;
    }
    
    public static BorrowService getInstance() {
        if (instance == null) {
            instance = new BorrowService();
        }
        return instance;
    }
    
    public boolean requestBorrow(String studentUsername, String bookId, 
                                java.time.LocalDate startDate, java.time.LocalDate endDate) {
        BookService bookService = BookService.getInstance();
        Book book = bookService.findBookById(bookId);
        
        if (book == null || !book.isAvailable()) {
            return false;
        }
        
        String requestId = "REQ" + (requestCounter++);
        BorrowRequest request = new BorrowRequest(requestId, studentUsername, bookId, startDate, endDate);
        borrowRequests.put(requestId, request);
        
        // Temporarily mark book as unavailable
        book.setAvailable(false);
        
        return true;
    }
    
    public List<BorrowRequest> getPendingRequestsForToday() {
        return borrowRequests.values().stream()
                .filter(request -> request.getStatus() == RequestStatus.PENDING)
                .filter(BorrowRequest::isForTodayOrBefore)
                .collect(Collectors.toList());
    }
    
    public boolean approveBorrowRequest(String requestId, String employeeUsername) {
        BorrowRequest request = borrowRequests.get(requestId);
        if (request == null || request.getStatus() != RequestStatus.PENDING) {
            return false;
        }
        
        request.setStatus(RequestStatus.APPROVED);
        
        // Create borrow record
        String recordId = "REC" + (recordCounter++);
        BorrowRecord record = new BorrowRecord(recordId, request.getStudentUsername(), 
                request.getBookId(), employeeUsername, java.time.LocalDate.now(), request.getEndDate());
        borrowRecords.put(recordId, record);
        
        // Update employee stats
        UserService userService = UserService.getInstance();
        Employee employee = userService.getEmployee(employeeUsername);
        if (employee != null) {
            employee.incrementBooksLent();
        }
        
        // Update student stats
        Student student = userService.getStudent(request.getStudentUsername());
        if (student != null) {
            student.incrementTotalBorrows();
            student.incrementNotReturnedCount();
        }
        
        return true;
    }
    
    public boolean returnBook(String recordId, String employeeUsername) {
        BorrowRecord record = borrowRecords.get(recordId);
        if (record == null || record.isReturned()) {
            return false;
        }
        
        record.markAsReturned();
        
        // Mark book as available again
        BookService bookService = BookService.getInstance();
        Book book = bookService.findBookById(record.getBookId());
        if (book != null) {
            book.setAvailable(true);
        }
        
        // Update employee stats
        UserService userService = UserService.getInstance();
        Employee employee = userService.getEmployee(employeeUsername);
        if (employee != null) {
            employee.incrementBooksReceived();
        }
        
        // Update student stats
        Student student = userService.getStudent(record.getStudentUsername());
        if (student != null) {
            student.decrementNotReturnedCount();
            if (record.isDelayed()) {
                student.incrementDelayedReturns();
            }
        }
        
        return true;
    }
    
    public List<BorrowRecord> getStudentBorrowHistory(String studentUsername) {
        return borrowRecords.values().stream()
                .filter(record -> record.getStudentUsername().equals(studentUsername))
                .collect(Collectors.toList());
    }
    
    public List<BorrowRecord> getAllBorrowRecords() {
        return new ArrayList<>(borrowRecords.values());
    }
    
    public int getTotalBorrowsCount() {
        return borrowRecords.size();
    }
    
    public int getActiveBorrowsCount() {
        return (int) borrowRecords.values().stream()
                .filter(record -> !record.isReturned())
                .count();
    }
}