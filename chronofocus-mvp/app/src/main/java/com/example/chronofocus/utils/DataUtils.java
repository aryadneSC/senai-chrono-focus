package com.example.chronofocus.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

public class DataUtils {

    private DataUtils(){};

    public static String returnActualDate(){
        return LocalDate.now(ZoneId.systemDefault()).toString();
    }


}
