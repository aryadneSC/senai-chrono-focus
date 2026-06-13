package com.example.chronofocus.repository;

import androidx.lifecycle.LiveData;

import com.example.chronofocus.data.MateriaDao;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;

import java.util.List;

public class SessionRepository {
    private LiveData<List<Materia>> materiasDoDia;
    private MateriaDao materiaDao;
    public SessionRepository(MateriaDao materiaDao, DaysWeek day){
        if (materiaDao == null)
            throw new NullPointerException("Materia dao não pode ser nulo");

        this.materiaDao = materiaDao;
        materiasDoDia = materiaDao.listarMaterias();
    }


}
