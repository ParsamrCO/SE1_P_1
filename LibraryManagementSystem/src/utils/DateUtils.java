package utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static boolean isDateInPastOrToday(LocalDate date) {
        return !date.isAfter(LocalDate.now());
    }
    
    public static boolean isDateInFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
    
    public static long getDaysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }
    
    public static boolean isValidBorrowPeriod(LocalDate start, LocalDate end) {
        return !start.isAfter(end) && !end.isBefore(LocalDate.now());
    }
}