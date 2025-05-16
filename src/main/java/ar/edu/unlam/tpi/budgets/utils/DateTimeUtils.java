package ar.edu.unlam.tpi.budgets.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public static LocalDateTime toLocalDateTime(String dateTime) {
        try {
            if (dateTime != null) {
                if (dateTime.length() == 10) {
                    return LocalDate.parse(dateTime, DATE_FORMATTER).atStartOfDay();
                }
                return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha no válido: " + dateTime, e);
        }
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
    }
}
