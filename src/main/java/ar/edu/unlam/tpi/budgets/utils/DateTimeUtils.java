package ar.edu.unlam.tpi.budgets.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public static LocalDateTime toLocalDateTime(String dateTime) {
        return dateTime != null ? LocalDateTime.parse(dateTime, FORMATTER) : null;
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }
}
