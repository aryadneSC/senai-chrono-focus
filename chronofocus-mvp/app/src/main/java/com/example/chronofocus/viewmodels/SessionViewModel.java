package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.chronofocus.data.ChronoDataBase;
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.repository.SessionTimerRepository;

public class SessionViewModel extends ViewModel {
    private final SessionTimerRepository repo;
    public SessionViewModel(SessionTimerRepository repo){
        this.repo = repo;
    }

    public static final ViewModelInitializer<SessionViewModel> initializer = new ViewModelInitializer<>(SessionViewModel.class,
            creationExtras  -> {
                Application app = creationExtras.get(APPLICATION_KEY);
                assert app != null;
                ChronoDataBase db = ChronoDataBase.getInstance(app);
                SessionTimerRepository repository = new SessionTimerRepository(db.sessionTimerDao());

                return new SessionViewModel(repository);
            }
    );

    public void insertSessionTimer(SessionTimer sessionTimer){
        if (sessionTimer == null)
            return;
        repo.insertSessionTimer(sessionTimer);
    }

    public void deleteSessionTimer(SessionTimer sessionTimer){
        if (sessionTimer == null)
            return;
        repo.deleteSessionTimer(sessionTimer);
    }
}
