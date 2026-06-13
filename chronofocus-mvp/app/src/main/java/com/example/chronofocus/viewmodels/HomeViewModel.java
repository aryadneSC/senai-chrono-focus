package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;


import com.example.chronofocus.data.ChronoDataBase;

import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.repository.MateriaRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MateriaRepository db;
    private LiveData<List<Materia>> session;
    public  HomeViewModel(MateriaRepository materiaRepository){
        this.db = materiaRepository;
    }

    public static ViewModelInitializer<HomeViewModel> inicializer = new ViewModelInitializer<>(HomeViewModel.class,
            creationExtras -> {

               Application app = creationExtras.get(APPLICATION_KEY);
               assert app != null;

               ChronoDataBase chronoDataBase = ChronoDataBase.getInstance(app);
               MateriaRepository repository = new MateriaRepository(chronoDataBase.materiaDao());

               return new HomeViewModel(repository);
    });

    public void addMateriasDoDia(DaysWeek day){
        session = db.listarMaterias(day);
    }

    public LiveData<List<Materia>> getMateriasDoDia(){
          return session;
    }
}
