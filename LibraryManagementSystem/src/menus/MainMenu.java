package menus;

import utils.InputUtils;

public class MainMenu {
    public void show() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("📚 سیستم مدیریت کتابخانه دانشگاه");
            System.out.println("=".repeat(50));
            System.out.println("1. ورود به عنوان مهمان");
            System.out.println("2. ورود به عنوان دانشجو");
            System.out.println("3. ورود به عنوان کارمند");
            System.out.println("4. ورود به عنوان مدیر");
            System.out.println("0. خروج از سیستم");
            System.out.println("-".repeat(50));
            
            int choice = InputUtils.readIntInput("🎯 لطفا گزینه مورد نظر را انتخاب کنید: ");
            
            switch (choice) {
                case 1:
                    showGuestMenu();
                    break;
                case 2:
                    showStudentMenu();
                    break;
                case 3:
                    showEmployeeMenu();
                    break;
                case 4:
                    showManagerMenu();
                    break;
                case 0:
                    System.out.println("👋 خروج از سیستم...");
                    return;
                default:
                    System.out.println("❌ گزینه نامعتبر! لطفا عدد بین 0 تا 4 وارد کنید.");
            }
        }
    }
    
    private void showGuestMenu() {
        System.out.println("\n🔍 در حال بارگذاری منوی مهمان...");
        GuestMenu guestMenu = new GuestMenu();
        guestMenu.show();
    }
    
    private void showStudentMenu() {
        System.out.println("\n🎓 در حال بارگذاری منوی دانشجو...");
        StudentMenu studentMenu = new StudentMenu();
        studentMenu.show();
    }
    
    private void showEmployeeMenu() {
        System.out.println("\n👨‍💼 در حال بارگذاری منوی کارمند...");
        EmployeeMenu employeeMenu = new EmployeeMenu();
        employeeMenu.show();
    }
    
    private void showManagerMenu() {
        System.out.println("\n👨‍💼 در حال بارگذاری منوی مدیر...");
        ManagerMenu managerMenu = new ManagerMenu();
        managerMenu.show();
    }
}