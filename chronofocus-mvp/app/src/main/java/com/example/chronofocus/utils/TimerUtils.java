package com.example.chronofocus.utils;

import android.annotation.SuppressLint;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerUtils { public static long convertToMillis(int seconds) {
        return (long)seconds * 1000;
    }

    @SuppressLint("DefaultLocale")
    public static String millisToFormattedTimeString(long millis) {

        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        if(hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else if(minutes > 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else if (seconds > 0) {
            return String.format("%02d", seconds);
        } else {
            throw new IllegalArgumentException("At millisToFormattedTimeString(), millis was negative");
        }
    }

}
