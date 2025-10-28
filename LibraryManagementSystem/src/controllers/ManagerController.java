package controllers;

import models.Employee;
import services.*;
import java.util.List;

public class ManagerController {
    private UserService userService;
    private ReportService reportService;
    
    public ManagerController() {
        this.userService = UserService.getInstance();
        this.reportService = ReportService.getInstance();
    }
    
    public boolean addEmployee(String username, String password, String fullName, String employeeId) {
        return userService.addEmployee(username, password, fullName, employeeId);
    }
    
    public ReportService.EmployeePerformance getEmployeePerformance(String employeeUsername) {
        return reportService.getEmployeePerformance(employeeUsername);
    }
    
    public ReportService.BorrowStatistics getBorrowStatistics() {
        return reportService.getBorrowStatistics();
    }
    
    public List<ReportService.StudentDelayStats> getTopDelayedStudents() {
        return reportService.getTopStudentsWithDelays();
    }
    
    public List<Employee> getAllEmployees() {
        return userService.getAllEmployees();
    }
}