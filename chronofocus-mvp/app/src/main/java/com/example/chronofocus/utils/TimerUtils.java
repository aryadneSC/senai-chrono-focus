package com.example.chronofocus.utils;

import java.sql.Time;

public class TimerUtils { public static long convertToMillis(int seconds) {
        return (long)seconds * 1000;
    }

    public static String millisToFormattedTimeString(long millis) {
        return new Time(millis).toString();
    }
}
