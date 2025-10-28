package models;

import java.time.LocalDate;

public class Student extends User {
    private String studentId;
    private String fullName;
    private LocalDate registrationDate;
    private int totalBorrows;
    private int delayedReturns;
    private int notReturnedCount;
    
    public Student(String username, String password, String fullName, String studentId) {
        super(username, password);
        this.fullName = fullName;
        this.studentId = studentId;
        this.registrationDate = LocalDate.now();
        this.totalBorrows = 0;
        this.delayedReturns = 0;
        this.notReturnedCount = 0;
    }
    
    public String getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public int getTotalBorrows() { return totalBorrows; }
    public int getDelayedReturns() { return delayedReturns; }
    public int getNotReturnedCount() { return notReturnedCount; }
    
    public void setTotalBorrows(int totalBorrows) { this.totalBorrows = totalBorrows; }
    public void setDelayedReturns(int delayedReturns) { this.delayedReturns = delayedReturns; }
    public void setNotReturnedCount(int notReturnedCount) { this.notReturnedCount = notReturnedCount; }
    
    public void incrementTotalBorrows() { this.totalBorrows++; }
    public void incrementDelayedReturns() { this.delayedReturns++; }
    public void incrementNotReturnedCount() { this.notReturnedCount++; }
    public void decrementNotReturnedCount() { this.notReturnedCount--; }
}