package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.chronofocus.data.MateriaDataBase;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.repository.MateriaRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MateriaRepository db;

    public  HomeViewModel(MateriaRepository materiaRepository){
        this.db = materiaRepository;
    }

    public static ViewModelInitializer<HomeViewModel> inicializer = new ViewModelInitializer<>(HomeViewModel.class,
            creationExtras -> {

               Application app = creationExtras.get(APPLICATION_KEY);
               assert app != null;
               MateriaDataBase materiaDataBase = MateriaDataBase.getInstance(app);
               MateriaRepository repository = new MateriaRepository(materiaDataBase.materiaDao());
               return new HomeViewModel(repository);
    });

    public LiveData<List<Materia>> getAllMaterias(){
        return db.listarMaterias();
    }



}
