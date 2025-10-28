package menus;

import controllers.EmployeeController;
import models.Book;
import models.BorrowRequest;
import services.BookService;
import services.ReportService;
import utils.InputUtils;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

public class EmployeeMenu {
    private EmployeeController controller;
    
    public EmployeeMenu() {
        this.controller = new EmployeeController();
    }
    
    public void show() {
        if (!authenticate()) {
            return;
        }
        
        while (true) {
            System.out.println("\n=== منوی کارمند ===");
            System.out.println("خوش آمدید، " + controller.getCurrentEmployee().getFullName());
            System.out.println("1. تغییر رمز عبور");
            System.out.println("2. ثبت کتاب جدید");
            System.out.println("3. جستجو و ویرایش کتاب");
            System.out.println("4. بررسی درخواست‌های امانت");
            System.out.println("5. دریافت کتاب بازگشتی");
            System.out.println("6. مدیریت وضعیت دانشجو");
            System.out.println("7. مشاهده تاریخچه امانت دانشجو");
            System.out.println("0. خروج");
            
            int choice = InputUtils.readIntInput("لطفا گزینه مورد نظر را انتخاب کنید: ");
            
            switch (choice) {
                case 1:
                    changePassword();
                    break;
                case 2:
                    addNewBook();
                    break;
                case 3:
                    searchAndEditBook();
                    break;
                case 4:
                    reviewBorrowRequests();
                    break;
                case 5:
                    receiveReturnedBook();
                    break;
                case 6:
                    manageStudentStatus();
                    break;
                case 7:
                    viewStudentHistory();
                    break;
                case 0:
                    controller.logout();
                    System.out.println("با موفقیت خارج شدید.");
                    return;
                default:
                    System.out.println("گزینه نامعتبر!");
            }
        }
    }
    
    private boolean authenticate() {
        System.out.println("\n=== ورود کارمند ===");
        String username = InputUtils.readStringInput("نام کاربری: ");
        String password = InputUtils.readPassword("رمز عبور: ");
        
        if (controller.login(username, password)) {
            System.out.println("✓ ورود موفقیت آمیز!");
            return true;
        } else {
            System.out.println("✗ نام کاربری یا رمز عبور اشتباه!");
            return false;
        }
    }
    
    private void changePassword() {
        System.out.println("\n=== تغییر رمز عبور ===");
        String newPassword = InputUtils.readPassword("رمز عبور جدید: ");
        
        if (controller.changePassword(newPassword)) {
            System.out.println("✓ رمز عبور با موفقیت تغییر یافت!");
        } else {
            System.out.println("✗ تغییر رمز عبور ناموفق!");
        }
    }
    
    private void addNewBook() {
        System.out.println("\n=== ثبت کتاب جدید ===");
        
        BookService bookService = BookService.getInstance();
        String bookId = bookService.generateBookId();
        String title = InputUtils.readStringInput("عنوان کتاب: ");
        String author = InputUtils.readStringInput("نام نویسنده: ");
        int year = InputUtils.readIntInput("سال انتشار: ");
        String isbn = InputUtils.readStringInput("شماره ISBN: ");
        
        Book book = new Book(bookId, title, author, year, isbn, true, 
                           controller.getCurrentEmployee().getUsername());
        
        if (controller.addBook(book)) {
            System.out.println("✓ کتاب با موفقیت ثبت شد!");
            System.out.println("شناسه کتاب: " + bookId);
        } else {
            System.out.println("✗ ثبت کتاب ناموفق!");
        }
    }
    
    private void searchAndEditBook() {
        System.out.println("\n=== جستجو و ویرایش کتاب ===");
        String title = InputUtils.readStringInput("عنوان کتاب برای جستجو: ");
        
        BookService bookService = BookService.getInstance();
        List<Book> books = bookService.searchBooksByTitle(title);
        
        if (books.isEmpty()) {
            System.out.println("📚 کتابی یافت نشد.");
            return;
        }
        
        System.out.println("\n📖 نتایج جستجو:");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%d. %s (شناسه: %s)%n", 
                (i + 1), book.getTitle(), book.getBookId());
        }
        
        String bookId = InputUtils.readStringInput("\nشناسه کتاب برای ویرایش: ");
        Book book = bookService.findBookById(bookId);
        
        if (book == null) {
            System.out.println("✗ کتاب یافت نشد!");
            return;
        }
        
        System.out.println("\nویرایش کتاب: " + book.getTitle());
        String newTitle = InputUtils.readStringInput("عنوان جدید (Enter برای بدون تغییر): ");
        String newAuthor = InputUtils.readStringInput("نویسنده جدید (Enter برای بدون تغییر): ");
        String newYearStr = InputUtils.readStringInput("سال جدید (Enter برای بدون تغییر): ");
        
        if (!newTitle.isEmpty()) book.setTitle(newTitle);
        if (!newAuthor.isEmpty()) book.setAuthor(newAuthor);
        if (!newYearStr.isEmpty()) {
            try {
                book.setPublicationYear(Integer.parseInt(newYearStr));
            } catch (NumberFormatException e) {
                System.out.println("✗ سال نامعتبر!");
            }
        }
        
        if (controller.updateBook(bookId, book)) {
            System.out.println("✓ کتاب با موفقیت ویرایش شد!");
        } else {
            System.out.println("✗ ویرایش کتاب ناموفق!");
        }
    }
    
    private void reviewBorrowRequests() {
        System.out.println("\n=== بررسی درخواست‌های امانت ===");
        
        List<BorrowRequest> requests = controller.getTodayPendingRequests();
        
        if (requests.isEmpty()) {
            System.out.println("📋 هیچ درخواست امانت pending برای امروز وجود ندارد.");
            return;
        }
        
        System.out.println("📋 درخواست‌های pending برای امروز:");
        for (int i = 0; i < requests.size(); i++) {
            BorrowRequest request = requests.get(i);
            BookService bookService = BookService.getInstance();
            Book book = bookService.findBookById(request.getBookId());
            String bookTitle = (book != null) ? book.getTitle() : "نامشخص";
            
            System.out.printf("%d. دانشجو: %s | کتاب: %s | تاریخ: %s تا %s%n",
                (i + 1), request.getStudentUsername(), bookTitle,
                request.getStartDate(), request.getEndDate());
        }
        
        String requestId = InputUtils.readStringInput("\nشناسه درخواست برای تایید: ");
        
        if (controller.processBorrowRequest(requestId)) {
            System.out.println("✓ درخواست با موفقیت تایید شد!");
        } else {
            System.out.println("✗ تایید درخواست ناموفق!");
        }
    }
    
    private void receiveReturnedBook() {
        System.out.println("\n=== دریافت کتاب بازگشتی ===");
        String recordId = InputUtils.readStringInput("شناسه رکورد امانت: ");
        
        if (controller.receiveReturnedBook(recordId)) {
            System.out.println("✓ کتاب با موفقیت بازگردانده شد!");
        } else {
            System.out.println("✗ دریافت کتاب ناموفق!");
        }
    }
    
    private void manageStudentStatus() {
        System.out.println("\n=== مدیریت وضعیت دانشجو ===");
        String username = InputUtils.readStringInput("نام کاربری دانشجو: ");
        
        if (controller.toggleStudentStatus(username)) {
            System.out.println("✓ وضعیت دانشجو تغییر یافت!");
        } else {
            System.out.println("✗ تغییر وضعیت ناموفق!");
        }
    }
    
    private void viewStudentHistory() {
        System.out.println("\n=== مشاهده تاریخچه دانشجو ===");
        String username = InputUtils.readStringInput("نام کاربری دانشجو: ");
        
        ReportService.StudentStatistics stats = controller.getStudentStatistics(username);
        
        if (stats == null) {
            System.out.println("✗ دانشجو یافت نشد!");
            return;
        }
        
        System.out.println("\n📊 آمار دانشجو:");
        System.out.println("──────────────────────────────────");
        System.out.printf("📚 تعداد کل امانت‌ها: %d%n", stats.totalBorrows);
        System.out.printf("🔄 کتاب‌های در دست امانت: %d%n", stats.notReturnedCount);
        System.out.printf("⏰ امانت‌های با تاخیر: %d%n", stats.delayedReturns);
        System.out.println("──────────────────────────────────");
    }
}