package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.chronofocus.ChronoFocusApp;
import com.example.chronofocus.data.ChronoDataBase;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.repository.MateriaRepository;

import java.util.List;

public class SubjectListViewModel extends ViewModel {
    private final MateriaRepository repo;

    public SubjectListViewModel(MateriaRepository repo){ this.repo = repo; }

    public static final ViewModelInitializer<SubjectListViewModel> initializer = new ViewModelInitializer<>(SubjectListViewModel.class,
            creationExtras  -> {
                ChronoFocusApp app = (ChronoFocusApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;

                MateriaRepository repository = app.getRepo();

                return new SubjectListViewModel(repository);
            }
    );


    public LiveData<List<Materia>> getAllMateria() {
        return repo.listarMaterias();
    }

}
