package com.example.chronofocus.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.utils.RoomTypeConverterUtils;



@Database(entities = {Materia.class, SessionTimer.class}, version = 4)
public abstract class ChronoDataBase extends RoomDatabase {
    private static ChronoDataBase instance;
    public abstract MateriaDao materiaDao();
    public abstract SessionTimerDao sessionTimerDao();
    public static ChronoDataBase getInstance(Context context){
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), ChronoDataBase.class, "dados-usuario").build();

        return instance;
    }
}
