package com.example.chronofocus.utils;

import android.annotation.SuppressLint;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerUtils { public static long convertToMillis(int seconds) {
        return (long)seconds * 1000;
    }

    public static String getStringFormatFor(long millis) {
        long oneMinute = 60000;
        long oneHour = 3600000;

        if (millis < oneMinute) {
            return "%02d";
        } else if (millis < oneHour) {
            return "%02d:%02d";
        } else {
            return "%02d:%02d:%02d";
        }
    }
    @SuppressLint("DefaultLocale")
    public static String millisToFormattedTimeString(long tickingMillis, long baseMillis) {
        long seconds = tickingMillis / 1000;

        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        String format = getStringFormatFor(baseMillis);
        switch (format) {
            case "%02d":
                return String.format(format, seconds);

            case "%02d:%02d":
                return String.format(format, minutes, seconds);

            default:
                return String.format(format, hours, minutes, seconds);
        }
    }
}
