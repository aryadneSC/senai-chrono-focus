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
    @Insert
    void insertMateria(List<Materia> materia);
    @Query("DELETE FROM Materia WHERE id = :id")
    public void deleteMateria(int id);

    @Query("SELECT * FROM Materia")
    public LiveData<List<Materia>> listarMaterias();

    @Query("SELECT * FROM Materia WHERE day LIKE :day AND (ultimo_dia_estudado <> :date OR ultimo_dia_estudado IS NULL)")
    public LiveData<List<Materia>> listarMaterias(String day, String date);

    @Query("UPDATE Materia SET ultimo_dia_estudado = :date WHERE id = :id")
    public void updateMateria(int id, String date);

    @Query("SELECT * FROM Materia WHERE day LIKE :day AND (ultimo_dia_estudado <> :date OR ultimo_dia_estudado IS NULL)")
    public LiveData<List<Materia>> listMateriasForSession(String day, String date);

    @Query("SELECT COUNT(*) FROM Materia WHERE day LIKE :day")
    int countMateriasOn(String day);

    @Query("SELECT * FROM Materia WHERE day LIKE :day AND (ultimo_dia_estudado <> :date OR ultimo_dia_estudado IS NULL)")
    List<Materia> listMateriasForSessionSync(String day, String date);
}
