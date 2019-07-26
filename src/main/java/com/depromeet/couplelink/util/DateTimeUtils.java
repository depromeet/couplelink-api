package com.depromeet.couplelink.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeUtils {
    private static final DateTimeFormatter BIRTH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateTimeUtils() {

    }

    public static LocalDateTime parseDate(String date) {
        TemporalAccessor temporalAccessor = BIRTH_DATE_FORMATTER.parse(date);
        return LocalDateTime.of(LocalDate.from(temporalAccessor), LocalTime.MIDNIGHT);
    }

}
