package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;


import com.example.chronofocus.ChronoFocusApp;
import com.example.chronofocus.data.ChronoDataBase;

import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.repository.MateriaRepository;
import com.example.chronofocus.repository.SessionRepository;

import java.util.List;
import java.util.function.Consumer;

public class HomeViewModel extends ViewModel {
    private final MateriaRepository db;
    private SessionRepository session;
    public  HomeViewModel(MateriaRepository materiaRepository, SessionRepository session)
    {
        this.db = materiaRepository;
        this.session = session;
    }

    public static ViewModelInitializer<HomeViewModel> inicializer = new ViewModelInitializer<>(HomeViewModel.class,
            creationExtras -> {

               ChronoFocusApp app = (ChronoFocusApp) creationExtras.get(APPLICATION_KEY);
               assert app != null;
               MateriaRepository materiaRepository = app.getRepo();
               SessionRepository sessionRepository = app.getSessionRepository();

               return new HomeViewModel(materiaRepository, sessionRepository);
    });

    public void addMateriasDoDia(String currentDay, String currentDate){
        session.carregarMateriasDoDia(currentDay, currentDate);
    }

    public LiveData<List<Materia>> getMateriasDoDia(){
          return session.getMateriasDoDia();
    }

    public void hasSessionOn(String day, Consumer<Boolean> callback) {
        session.hasSessionOn(day, callback);
    }}
