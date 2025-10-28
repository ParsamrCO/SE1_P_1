package models;

import java.time.LocalDate;

public class Employee extends User {
    private String employeeId;
    private String fullName;
    private LocalDate hireDate;
    private int booksRegistered;
    private int booksLent;
    private int booksReceived;
    
    public Employee(String username, String password, String fullName, String employeeId) {
        super(username, password);
        this.fullName = fullName;
        this.employeeId = employeeId;
        this.hireDate = LocalDate.now();
        this.booksRegistered = 0;
        this.booksLent = 0;
        this.booksReceived = 0;
    }
    
    public String getEmployeeId() { return employeeId; }
    public String getFullName() { return fullName; }
    public LocalDate getHireDate() { return hireDate; }
    public int getBooksRegistered() { return booksRegistered; }
    public int getBooksLent() { return booksLent; }
    public int getBooksReceived() { return booksReceived; }
    
    public void incrementBooksRegistered() { this.booksRegistered++; }
    public void incrementBooksLent() { this.booksLent++; }
    public void incrementBooksReceived() { this.booksReceived++; }
}