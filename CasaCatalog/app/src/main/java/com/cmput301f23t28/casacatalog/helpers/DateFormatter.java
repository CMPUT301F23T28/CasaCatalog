package com.cmput301f23t28.casacatalog.helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatter {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DateFormatter(){}

    public static String getFormattedDate(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("MMM. d, yyyy"));
    }

    public static String getFormattedDate(Date date){
        return getFormattedDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
