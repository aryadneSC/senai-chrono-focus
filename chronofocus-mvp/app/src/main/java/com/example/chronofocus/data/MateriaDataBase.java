package com.example.chronofocus.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.chronofocus.model.Materia;


@Database(entities = {Materia.class}, version = 1)
public abstract class MateriaDataBase extends RoomDatabase {
    private static MateriaDataBase instance;
    public abstract MateriaDao materiaDao();

    public static MateriaDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MateriaDataBase.class, "dados-usuario").build();
        }
        return instance;
    }
}
