package com.example.chronofocus.repository;

import androidx.lifecycle.LiveData;

import com.example.chronofocus.data.MateriaDao;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.utils.ThreadsManager;

import java.util.List;

public class MateriaRepository  {
    private MateriaDao db;

    public MateriaRepository(MateriaDao db){
        this.db = db;
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
    public void deletarMaterias(int id){
        ThreadsManager.startTask(new Runnable() {
            @Override
            public void run() {
                db.deleteMateria(id);
            }
        });
    }



}
