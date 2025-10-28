package utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtils {
    private static Scanner scanner = new Scanner(System.in);
    
    public static int readIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return value;
        } catch (Exception e) {
            scanner.nextLine(); // clear buffer
            return -1;
        }
    }
    
    public static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public static LocalDate readDateInput(String prompt) {
        System.out.print(prompt + " (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().trim();
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("فرمت تاریخ نامعتبر! از فرمت YYYY-MM-DD استفاده کنید.");
            return null;
        }
    }
    
    public static String readPassword(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}