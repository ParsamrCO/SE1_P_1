import menus.MainMenu;
import models.Book;
import models.Employee;
import models.Student;
import services.BookService;
import services.UserService;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("🚀 در حال راه‌اندازی سیستم مدیریت کتابخانه دانشگاه...");
        
        // Initialize sample data for testing
        initializeSampleData();
        
        // Start the application
        System.out.println("✅ سیستم آماده است!\n");
        
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        
        System.out.println("\n🙏 با تشکر از استفاده شما از سیستم مدیریت کتابخانه!");
    }
    
    private static void initializeSampleData() {
        System.out.println("📦 در حال بارگذاری داده‌های نمونه...");
        
        UserService userService = UserService.getInstance();
        BookService bookService = BookService.getInstance();
        
        // Add sample employees
        userService.addEmployee("admin", "admin123", "مدیر سیستم", "EMP001");
        userService.addEmployee("emp1", "emp123", "کارمند یک", "EMP002");
        userService.addEmployee("emp2", "emp123", "کارمند دو", "EMP003");
        
        // Add sample students
        userService.registerStudent("stu1", "stu123", "دانشجو یک", "STU001");
        userService.registerStudent("stu2", "stu123", "دانشجو دو", "STU002");
        userService.registerStudent("stu3", "stu123", "دانشجو سه", "STU003");
        userService.registerStudent("ahmadi", "123456", "احمد احمدی", "STU004");
        userService.registerStudent("moradi", "123456", "مریم مرادی", "STU005");
        
        // Add sample books
        Book book1 = new Book(bookService.generateBookId(), "طراحی الگوریتم", 
                             "جان هاپکروفت", 2006, "ISBN-001", true, "emp1");
        Book book2 = new Book(bookService.generateBookId(), "پایگاه داده", 
                             "راماکریشنان", 2002, "ISBN-002", true, "emp1");
        Book book3 = new Book(bookService.generateBookId(), "مهندسی نرم‌افزار", 
                             "یان سامرویل", 2015, "ISBN-003", true, "emp2");
        Book book4 = new Book(bookService.generateBookId(), "ساختمان داده‌ها", 
                             "مارک آلن وایس", 2012, "ISBN-004", true, "emp2");
        Book book5 = new Book(bookService.generateBookId(), "هوش مصنوعی", 
                             "راسل و نورویگ", 2020, "ISBN-005", true, "emp1");
        Book book6 = new Book(bookService.generateBookId(), "شبکه‌های کامپیوتری", 
                             "اندرو تننبام", 2010, "ISBN-006", true, "emp2");
        Book book7 = new Book(bookService.generateBookId(), "سیستم‌عامل", 
                             "ویلیام استالینگز", 2018, "ISBN-007", true, "emp1");
        Book book8 = new Book(bookService.generateBookId(), "برنامه‌نویسی پیشرفته", 
                             "رابرت سدجویک", 2019, "ISBN-008", true, "emp2");
        
        bookService.addBook(book1, "emp1");
        bookService.addBook(book2, "emp1");
        bookService.addBook(book3, "emp2");
        bookService.addBook(book4, "emp2");
        bookService.addBook(book5, "emp1");
        bookService.addBook(book6, "emp2");
        bookService.addBook(book7, "emp1");
        bookService.addBook(book8, "emp2");
        
        // Display initialization summary
        System.out.println("✅ داده‌های نمونه بارگذاری شد:");
        System.out.println("   👨‍💼 کارمندان: " + userService.getAllEmployees().size() + " نفر");
        System.out.println("   👨‍🎓 دانشجویان: " + userService.getAllStudents().size() + " نفر");
        System.out.println("   📚 کتاب‌ها: " + bookService.getAllBooks().size() + " عنوان");
        
        // Display sample login information
        System.out.println("\n🔐 اطلاعات نمونه برای تست:");
        System.out.println("   مدیر سیستم:");
        System.out.println("     کاربری: admin | رمز: admin123");
        System.out.println("   کارمندان:");
        System.out.println("     کاربری: emp1 | رمز: emp123");
        System.out.println("     کاربری: emp2 | رمز: emp123");
        System.out.println("   دانشجویان:");
        System.out.println("     کاربری: stu1 | رمز: stu123");
        System.out.println("     کاربری: ahmadi | رمز: 123456");
        System.out.println("     کاربری: moradi | رمز: 123456");
    }
}