package com.example.chronofocus.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum DaysWeek {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURDAY,
    FRIDAY,
    SATURDAY;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DaysWeek whoIsDay(){
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        return DaysWeek.values()[day.getValue() - 1];
    }

}
