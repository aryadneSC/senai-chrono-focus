package com.example.chronofocus.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.chronofocus.data.MateriaDao;
import com.example.chronofocus.data.MateriaDataBase;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MateriaRepository  {
    private MateriaDao db;

    public MateriaRepository(Context context){
        db = MateriaDataBase.getInstance(context).materiaDao();
    }
    public LiveData<List<Materia>> listarMaterias(int limit, DaysWeek day){
        db.listarMaterias(limit, day);
    }
    public LiveData<List<Materia>> listarMaterias(){
        return db.listarMaterias();
    }
    public void inserirMaterias(Materia materia){
        db.insertMateria(materia);
    }
    public void deletarMaterias(Materia materia){
        db.deleteMateria(materia);
    }

}
