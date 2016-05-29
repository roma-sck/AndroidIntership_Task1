package com.example.sck.androidintership_task1.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateConverter {
    private static final String DATE_FORMAT = "MMM d, yyyy";
    private static final int SEC_TO_MILLIS_COEFFICIENT = 1000;
    private static final String LOCALE_LANGUAGE = "uk";
    private static final String LOCALE_REGION = "UA";
    private static final String ONE_DAY_TEXT = " день";
    private static final String FEW_DAYS_TEXT = " дні";
    private static final String MANY_DAYS_TEXT = " днів";

    public static String convertDate(long dateInSeconds) {
        long dateInMillis = dateInSeconds * SEC_TO_MILLIS_COEFFICIENT;
        Date date = new Date(dateInMillis);
        Locale ukrLocale = new Locale(LOCALE_LANGUAGE, LOCALE_REGION);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, ukrLocale);
        String result = simpleDateFormat.format(date);
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        return result;
    }

    public static String getDaysLeft(long startDateInSeconds) {
        long dateInSecNow = new Date().getTime()/SEC_TO_MILLIS_COEFFICIENT;
        long daysLeft = TimeUnit.SECONDS.toDays(startDateInSeconds - dateInSecNow);
        String result;
        if (daysLeft == 1) {
            result = daysLeft + ONE_DAY_TEXT;
        } else if (daysLeft > 1 && daysLeft < 5) {
            result = daysLeft + FEW_DAYS_TEXT;
        } else {
            result = daysLeft + MANY_DAYS_TEXT;
        }
        return result;
    }
}
