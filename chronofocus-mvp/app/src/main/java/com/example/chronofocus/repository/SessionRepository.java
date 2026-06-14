package com.example.chronofocus.repository;

import androidx.lifecycle.LiveData;

import com.example.chronofocus.data.MateriaDao;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.utils.DataUtils;
import com.example.chronofocus.utils.ThreadsManager;

import java.util.List;

public class SessionRepository {
    private LiveData<List<Materia>> materiasDoDia;
    private MateriaDao materiaDao;
    public SessionRepository(MateriaDao materiaDao){
        if (materiaDao == null)
            throw new NullPointerException("Materia dao não pode ser nulo");
        this.materiaDao = materiaDao;

    }
    public void carregarMateriasDoDia(String currentDay, String currentData){
        materiasDoDia = materiaDao.listarMaterias("%" + currentDay + "%", currentData);
    }
    public void updateUltimoDiaEstudado(int id, String date){
        ThreadsManager.startTask(new Runnable() {
            @Override
            public void run() {
                materiaDao.updateMateria(id, date);
            }
        });
    }

    public LiveData<List<Materia>> getMateriasDoDia(){
        return materiasDoDia;
    }
}
