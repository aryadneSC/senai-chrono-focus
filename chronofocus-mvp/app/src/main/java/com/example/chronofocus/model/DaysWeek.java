package com.example.chronofocus.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Nullable
    public static DaysWeek stringToEnum(String txt){
        switch (txt){
            case "Segunda":
                return MONDAY;
            case "Terça":
                return TUESDAY;
            case "Quarta":
                return WEDNESDAY;
            case "Quinta":
                return THURSDAY;
            case "Sexta":
                return FRIDAY;
            case "Sábado":
                return SATURDAY;
            case "Domingo":
                return SUNDAY;
            default:
                return null;
        }
    }



    @Nullable
    public static DaysWeek stringToEnum(CharSequence charSequence){
        return stringToEnum(charSequence.toString());
    }

    @NonNull
    @Override
    public String toString(){
        return name;
    }

}
