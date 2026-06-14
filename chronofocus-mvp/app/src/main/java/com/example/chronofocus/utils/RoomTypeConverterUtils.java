package com.example.chronofocus.utils;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.chronofocus.model.DaysWeek;

import java.util.ArrayList;
import java.util.stream.Collectors;



public class RoomTypeConverterUtils {
    @TypeConverter
    public static String arrayDayToString(ArrayList<DaysWeek> days){
        if (days == null) return "";
        return  days.stream().map(DaysWeek::name).collect(Collectors.joining(","));
    }

    @TypeConverter
    public  static ArrayList<DaysWeek> stringToDayEnumArray(String day){
        if (day == null || day.isEmpty()) return null;
       ArrayList<DaysWeek> daysArrayList = new ArrayList<>();
       String[] days = day.split(",");
       for (String s : days){
           daysArrayList.add(DaysWeek.valueOf(s));
       }

       return  daysArrayList;
    }
}
