package menus;

import controllers.GuestController;
import models.Book;
import services.ReportService;
import utils.InputUtils;

import java.util.List;

public class GuestMenu {
    private GuestController controller;
    
    public GuestMenu() {
        this.controller = new GuestController();
    }
    
    public void show() {
        while (true) {
            System.out.println("\n=== منوی مهمان ===");
            System.out.println("1. مشاهده تعداد دانشجویان");
            System.out.println("2. جستجوی کتاب بر اساس عنوان");
            System.out.println("3. مشاهده آمار کلی کتابخانه");
            System.out.println("0. بازگشت به منوی اصلی");
            
            int choice = InputUtils.readIntInput("لطفا گزینه مورد نظر را انتخاب کنید: ");
            
            switch (choice) {
                case 1:
                    showStudentCount();
                    break;
                case 2:
                    searchBooksByTitle();
                    break;
                case 3:
                    showLibraryStatistics();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("گزینه نامعتبر!");
            }
        }
    }
    
    private void showStudentCount() {
        int count = controller.getRegisteredStudentsCount();
        System.out.println("\n👨‍🎓 تعداد دانشجویان ثبت‌نام شده: " + count + " نفر");
    }
    
    private void searchBooksByTitle() {
        String title = InputUtils.readStringInput("عنوان کتاب برای جستجو: ");
        List<Book> books = controller.searchBooksByTitle(title);
        
        if (books.isEmpty()) {
            System.out.println("📚 کتابی با این عنوان یافت نشد.");
        } else {
            System.out.println("\n📖 نتایج جستجو:");
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                System.out.printf("%d. %s | نویسنده: %s | سال: %d%n",
                    (i + 1), book.getTitle(), book.getAuthor(), book.getPublicationYear());
            }
        }
    }
    
    private void showLibraryStatistics() {
        ReportService.LibraryStatistics stats = controller.getSimpleStatistics();
        if (stats != null) {
            System.out.println("\n📊 آمار کلی کتابخانه:");
            System.out.println("──────────────────────────────────");
            System.out.println("👨‍🎓 تعداد دانشجویان: " + stats.totalStudents);
            System.out.println("📚 تعداد کل کتاب‌ها: " + stats.totalBooks);
            System.out.println("📋 تعداد کل امانت‌ها: " + stats.totalBorrows);
            System.out.println("🔄 امانت‌های فعال: " + stats.activeBorrows);
            System.out.println("──────────────────────────────────");
        }
    }
}