package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.chronofocus.ChronoFocusApp;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionTimer;
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
    private Queue<SessionTimer> pendingTimers;
    public SessionViewModel(SessionTimerRepository timerRepo, MateriaRepository materiaRepo, SessionRepository sessionRepo){
        this.sessionRepo = sessionRepo;
        this.timerRepo = timerRepo;
        this.materiaRepo = materiaRepo;
        pendingTimers = new LinkedList<>();
        //

        //temporario apenas para teste
        Materia materia1 = new Materia("Russo",
                TimerUtils.convertToMillis(10),
                DaysWeek.FRIDAY, 3);

        Materia materia2 = new Materia("ingles",
                TimerUtils.convertToMillis(10),
                DaysWeek.FRIDAY, 2);

        Materia materia3 = new Materia("Português",
                TimerUtils.convertToMillis(10),
                DaysWeek.FRIDAY, 1);

        List<Materia> materias = new ArrayList<>();

        materias.add(materia1);
        materias.add(materia2);
        materias.add(materia3);

        //Prioridade decrescente
        materias.sort(
                Comparator.comparingInt(Materia::getPriority).reversed()
        );

        //var subjects = sessionRepo.getPriorityOrderedMaterias();

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


    public void insertSessionTimer(SessionTimer sessionTimer){
        if (sessionTimer == null)
            return;
        timerRepo.insertSessionTimer(sessionTimer);
    }

    public void deleteSessionTimer(SessionTimer sessionTimer){
        if (sessionTimer == null)
            return;
        timerRepo.deleteSessionTimer(sessionTimer);
    }
    /*
    public Materia getNextMateria() {
        return sessionMaterias.poll();
    }
    */

}
