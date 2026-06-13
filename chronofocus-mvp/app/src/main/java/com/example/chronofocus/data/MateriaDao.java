package com.example.chronofocus.data;

import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MateriaDao {
    @Insert
    public void insertMateria(Materia materia);

    @Delete
    public void deleteMateria(Materia materia);

    @Query("SELECT * FROM Materia")
    public LiveData<List<Materia>> listarMaterias();

    @Query("SELECT * FROM Materia WHERE day = :day LIMIT:limit")
    public LiveData<List<Materia>> listarMaterias(int limit, DaysWeek day);
}
