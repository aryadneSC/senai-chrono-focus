package com.example.chronofocus.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.chronofocus.data.MateriaDao;
import com.example.chronofocus.data.MateriaDataBase;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.utils.ThreadsManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MateriaRepository  {
    private MateriaDao db;

    public MateriaRepository(MateriaDao db){
        this.db = db;
    }
    public LiveData<List<Materia>> listarMaterias(DaysWeek day){
        return db.listarMaterias(day);
    }
    public LiveData<List<Materia>> listarMaterias(){
        return db.listarMaterias();
    }

    public void inserirMaterias(Materia materia){
        ThreadsManager.startTask(new Runnable() {
            @Override
            public void run(){
                db.insertMateria(materia);
            }
        });
    }
    public void deletarMaterias(Materia materia){
        ThreadsManager.startTask(new Runnable() {
            @Override
            public void run() {
                db.deleteMateria(materia);
            }
        });
    }

    public void updateMateria(){

    }

}
