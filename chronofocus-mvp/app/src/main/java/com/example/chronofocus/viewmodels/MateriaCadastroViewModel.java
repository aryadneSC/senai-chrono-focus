package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.chronofocus.data.ChronoDataBase;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.repository.MateriaRepository;

public class MateriaCadastroViewModel extends ViewModel {
    private final MateriaRepository repo;
    public MateriaCadastroViewModel(MateriaRepository repo){
        this.repo = repo;
    }

    public static final ViewModelInitializer<MateriaCadastroViewModel> initializer = new ViewModelInitializer<>(MateriaCadastroViewModel.class,
         creationExtras  -> {
                Application app = creationExtras.get(APPLICATION_KEY);
             assert app != null;
             ChronoDataBase db = ChronoDataBase.getInstance(app);
             MateriaRepository repository = new MateriaRepository(db.materiaDao());

                 return new MateriaCadastroViewModel(repository);
            }
    );

    public void inserirMateria(Materia materia){
        if (materia == null)
            return;
        repo.inserirMaterias(materia);
    }


}
