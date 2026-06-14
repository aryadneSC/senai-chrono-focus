package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.chronofocus.ChronoFocusApp;
import com.example.chronofocus.activities.SessionActivity;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.model.Status;
import com.example.chronofocus.repository.MateriaRepository;
import com.example.chronofocus.repository.SessionRepository;
import com.example.chronofocus.repository.SessionTimerRepository;
import com.example.chronofocus.utils.TimerUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SessionViewModel extends ViewModel {
    private final SessionTimerRepository timerRepo;
    private final SessionRepository sessionRepo;
    private final MateriaRepository materiaRepo;

    // usar shared preferences pra salvar sessionTimer no repo
    private SessionTimer timer;
    private String nextMateriaName = "";
    public SessionTimer getSessionTimer() {
        return new SessionTimer(timer);
    }

    public String getNextMateriaName() {
        // temporario
        return "Geografia";
    }

    public SessionViewModel(SessionTimerRepository timerRepo, MateriaRepository materiaRepo, SessionRepository sessionRepo){
        this.sessionRepo = sessionRepo;
        this.timerRepo = timerRepo;
        this.materiaRepo = materiaRepo;

        ArrayList<DaysWeek> days = new ArrayList<>();
        days.add(DaysWeek.FRIDAY);
        Materia proximaMateria = new Materia("Português", 432131, days, 2);

        timer = new SessionTimer(proximaMateria.getId(), proximaMateria.getBaseTime(), Status.INACTIVE);
    }

    public static final ViewModelInitializer<SessionViewModel> initializer = new ViewModelInitializer<>(SessionViewModel.class,
            creationExtras  -> {
                ChronoFocusApp app = (ChronoFocusApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SessionRepository _sessionRepository = app.getSessionRepository();
                SessionTimerRepository _timerRepo = app.getTimerRepo();
                MateriaRepository _materiaRepo = app.getRepo();
                return new SessionViewModel(_timerRepo, _materiaRepo, _sessionRepository);
            }
    );

}
