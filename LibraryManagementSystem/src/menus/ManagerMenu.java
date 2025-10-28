package menus;

import controllers.ManagerController;
import models.Employee;
import services.ReportService;
import utils.InputUtils;

import java.util.List;

public class ManagerMenu {
    private ManagerController controller;
    
    public ManagerMenu() {
        this.controller = new ManagerController();
    }
    
    public void show() {
        System.out.println("\n=== منوی مدیر ===");
        
        while (true) {
            System.out.println("\n1. تعریف کارمند جدید");
            System.out.println("2. مشاهده عملکرد کارمند");
            System.out.println("3. مشاهده آمار امانت‌ها");
            System.out.println("4. مشاهده دانشجویان با بیشترین تاخیر");
            System.out.println("0. بازگشت به منوی اصلی");
            
            int choice = InputUtils.readIntInput("لطفا گزینه مورد نظر را انتخاب کنید: ");
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployeePerformance();
                    break;
                case 3:
                    viewBorrowStatistics();
                    break;
                case 4:
                    viewTopDelayedStudents();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("گزینه نامعتبر!");
            }
        }
    }
    
    private void addEmployee() {
        System.out.println("\n=== تعریف کارمند جدید ===");
        String username = InputUtils.readStringInput("نام کاربری: ");
        String password = InputUtils.readPassword("رمز عبور: ");
        String fullName = InputUtils.readStringInput("نام کامل: ");
        String employeeId = InputUtils.readStringInput("شناسه کارمند: ");
        
        if (controller.addEmployee(username, password, fullName, employeeId)) {
            System.out.println("✓ کارمند با موفقیت اضافه شد!");
        } else {
            System.out.println("✗ اضافه کردن کارمند ناموفق! نام کاربری تکراری است.");
        }
    }
    
    private void viewEmployeePerformance() {
        System.out.println("\n=== مشاهده عملکرد کارمند ===");
        
        List<Employee> employees = controller.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("👨‍💼 هیچ کارمندی ثبت نشده است.");
            return;
        }
        
        System.out.println("👨‍💼 لیست کارمندان:");
        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            System.out.printf("%d. %s (کاربری: %s)%n", 
                (i + 1), emp.getFullName(), emp.getUsername());
        }
        
        String username = InputUtils.readStringInput("\nنام کاربری کارمند: ");
        ReportService.EmployeePerformance perf = controller.getEmployeePerformance(username);
        
        if (perf == null) {
            System.out.println("✗ کارمند یافت نشد!");
            return;
        }
        
        System.out.println("\n📊 عملکرد کارمند:");
        System.out.println("──────────────────────────────────");
        System.out.printf("📚 کتاب‌های ثبت شده: %d%n", perf.booksRegistered);
        System.out.printf("📖 کتاب‌های امانت داده شده: %d%n", perf.booksLent);
        System.out.printf("🔄 کتاب‌های دریافت شده: %d%n", perf.booksReceived);
        System.out.println("──────────────────────────────────");
    }
    
    private void viewBorrowStatistics() {
        System.out.println("\n=== آمار امانت‌ها ===");
        
        ReportService.BorrowStatistics stats = controller.getBorrowStatistics();
        
        if (stats == null) {
            System.out.println("✗ خطا در دریافت آمار!");
            return;
        }
        
        System.out.println("📊 آمار کلی امانت‌ها:");
        System.out.println("──────────────────────────────────");
        System.out.printf("📋 تعداد کل درخواست‌ها: %d%n", stats.totalRequests);
        System.out.printf("📚 تعداد کل امانت‌ها: %d%n", stats.totalBorrows);
        System.out.printf("📅 میانگین روزهای امانت: %.1f روز%n", stats.averageBorrowDays);
        System.out.println("──────────────────────────────────");
    }
    
    private void viewTopDelayedStudents() {
        System.out.println("\n=== دانشجویان با بیشترین تاخیر ===");
        
        List<ReportService.StudentDelayStats> topStudents = controller.getTopDelayedStudents();
        
        if (topStudents.isEmpty()) {
            System.out.println("🎓 هیچ دانشجویی با تاخیر وجود ندارد.");
            return;
        }
        
        System.out.println("🏆 10 دانشجوی با بیشترین تاخیر:");
        System.out.println("──────────────────────────────────");
        for (int i = 0; i < topStudents.size(); i++) {
            ReportService.StudentDelayStats student = topStudents.get(i);
            System.out.printf("%d. %s - %d تاخیر%n", 
                (i + 1), student.fullName, student.delayCount);
        }
        System.out.println("──────────────────────────────────");
    }
}