package models;

import java.time.LocalDate;

public class BorrowRecord {
    private String recordId;
    private String studentUsername;
    private String bookId;
    private String employeeUsername;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returned;
    private boolean delayed;
    
    public BorrowRecord(String recordId, String studentUsername, String bookId, 
                       String employeeUsername, LocalDate borrowDate, LocalDate dueDate) {
        this.recordId = recordId;
        this.studentUsername = studentUsername;
        this.bookId = bookId;
        this.employeeUsername = employeeUsername;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = false;
        this.delayed = false;
    }
    
    // Getters
    public String getRecordId() { return recordId; }
    public String getStudentUsername() { return studentUsername; }
    public String getBookId() { return bookId; }
    public String getEmployeeUsername() { return employeeUsername; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return returned; }
    public boolean isDelayed() { return delayed; }
    
    // Setters
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setReturned(boolean returned) { this.returned = returned; }
    public void setDelayed(boolean delayed) { this.delayed = delayed; }
    
    public void markAsReturned() {
        this.returned = true;
        this.returnDate = LocalDate.now();
        this.delayed = returnDate.isAfter(dueDate);
    }
}