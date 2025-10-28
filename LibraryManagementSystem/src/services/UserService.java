package services;

import models.*;
import java.util.*;

public class UserService {
    private static UserService instance;
    private Map<String, User> users;
    private Map<String, Student> students;
    private Map<String, Employee> employees;
    
    private UserService() {
        users = new HashMap<>();
        students = new HashMap<>();
        employees = new HashMap<>();
    }
    
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    
    // Student methods
    public boolean registerStudent(String username, String password, String fullName, String studentId) {
        if (users.containsKey(username)) {
            return false;
        }
        
        Student student = new Student(username, password, fullName, studentId);
        users.put(username, student);
        students.put(username, student);
        return true;
    }
    
    public Student loginStudent(String username, String password) {
        User user = users.get(username);
        if (user instanceof Student && user.authenticate(password)) {
            return (Student) user;
        }
        return null;
    }
    
    // Employee methods
    public boolean addEmployee(String username, String password, String fullName, String employeeId) {
        if (users.containsKey(username)) {
            return false;
        }
        
        Employee employee = new Employee(username, password, fullName, employeeId);
        users.put(username, employee);
        employees.put(username, employee);
        return true;
    }
    
    public Employee loginEmployee(String username, String password) {
        User user = users.get(username);
        if (user instanceof Employee && user.authenticate(password)) {
            return (Employee) user;
        }
        return null;
    }
    
    // Utility methods
    public boolean toggleStudentStatus(String username) {
        Student student = students.get(username);
        if (student != null) {
            student.setActive(!student.isActive());
            return true;
        }
        return false;
    }
    
    public int getTotalStudentsCount() {
        return students.size();
    }
    
    public Student getStudent(String username) {
        return students.get(username);
    }
    
    public Employee getEmployee(String username) {
        return employees.get(username);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }
}