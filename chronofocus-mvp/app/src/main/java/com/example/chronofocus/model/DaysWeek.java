package com.example.chronofocus.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

public enum DaysWeek {
    SUNDAY( "Domingo"),
    MONDAY ("Segunda"),
    TUESDAY( "Terça"),
    WEDNESDAY( "Quarta"),
    THURSDAY( "Quinta"),
    FRIDAY( "Sexta"),
    SATURDAY( "Sábado");


    private final String name;
    DaysWeek(String name){
        this.name = name;
    }

    public static DaysWeek getCurrentDay(){
        String date = LocalDate.now(ZoneId.systemDefault()).getDayOfWeek().toString();
        return DaysWeek.valueOf(date);
    }


    @NonNull
    @Override
    public String toString(){
        return name;
    }

}
