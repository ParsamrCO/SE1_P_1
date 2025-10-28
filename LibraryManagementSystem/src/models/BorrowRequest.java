package models;

import java.time.LocalDate;

public class BorrowRequest {
    private String requestId;
    private String studentUsername;
    private String bookId;
    private LocalDate startDate;
    private LocalDate endDate;
    private RequestStatus status;
    private LocalDate requestDate;
    
    public BorrowRequest(String requestId, String studentUsername, String bookId, 
                        LocalDate startDate, LocalDate endDate) {
        this.requestId = requestId;
        this.studentUsername = studentUsername;
        this.bookId = bookId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = RequestStatus.PENDING;
        this.requestDate = LocalDate.now();
    }
    
    // Getters
    public String getRequestId() { return requestId; }
    public String getStudentUsername() { return studentUsername; }
    public String getBookId() { return bookId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public RequestStatus getStatus() { return status; }
    public LocalDate getRequestDate() { return requestDate; }
    
    // Setters
    public void setStatus(RequestStatus status) { this.status = status; }
    
    public boolean isForTodayOrBefore() {
        return !startDate.isAfter(LocalDate.now());
    }
}