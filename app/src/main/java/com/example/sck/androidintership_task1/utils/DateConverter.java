package com.example.sck.androidintership_task1.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final String DATE_FORMAT = "MMM d, yyyy";
    private static final int SEC_TO_MILLIS_COEFFICIENT = 1000;
    private static final String LOCALE_LANGUAGE = "uk";
    private static final String LOCALE_REGION = "UA";

    public static String convertDate(long dateInSeconds) {
        long dateInMillis = dateInSeconds * SEC_TO_MILLIS_COEFFICIENT;
        Date date = new Date(dateInMillis);
        Locale ukrLocale = new Locale(LOCALE_LANGUAGE, LOCALE_REGION);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, ukrLocale);
        return simpleDateFormat.format(date);
    }

    public static String getDaysLeft(long StartDateInSeconds) {
        Date date = new Date(StartDateInSeconds * SEC_TO_MILLIS_COEFFICIENT);
        return DateUtils.getRelativeTimeSpanString(date.getTime()).toString();
    }
}
