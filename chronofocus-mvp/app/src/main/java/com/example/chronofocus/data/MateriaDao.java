package com.example.chronofocus.data;

import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface MateriaDao {
    @Insert
    public void insertMateria(Materia materia);

    @Delete
    public void deleteMateria(Materia materia);

    @Query("SELECT * FROM Materia")
    public LiveData<List<Materia>> listarMaterias();

    @Query("SELECT * FROM Materia WHERE day = :day AND (ultimo_dia_estudado <> :date OR ultimo_dia_estudado IS NULL)")
    public LiveData<List<Materia>> listarMaterias(DaysWeek day, String date);

    @Query("UPDATE Materia SET ultimo_dia_estudado = :date WHERE id = :id")
    public void updateMateria(int id, String date);

    @Query("UPDATE Materia SET nome = :name WHERE id = :id")
    public void updateMateria(String name, int id);

    @Query("UPDATE Materia SET day = :day WHERE id = :id")
    public void updateMateria(DaysWeek day, int id);



}
