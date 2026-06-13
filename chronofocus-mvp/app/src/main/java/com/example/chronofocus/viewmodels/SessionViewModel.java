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
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.repository.MateriaRepository;
import com.example.chronofocus.repository.SessionTimerRepository;
import com.example.chronofocus.utils.TimerUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SessionViewModel extends ViewModel {
    private final SessionTimerRepository timerRepo;
    private final MateriaRepository materiaRepo;
    private Queue<Materia> sessionMaterias;
    public SessionViewModel(SessionTimerRepository timerRepo, MateriaRepository materiaRepo){
        this.timerRepo = timerRepo;
        this.materiaRepo = materiaRepo;
        //
        // DISCLAIMER: provisorio, o ideal é obter por consulta filtrada pelo MateriaRepository

        //Obtemos as materias do dia via repo
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

        //Ordenamos por prioridade decrescente
        materias.sort(
                Comparator.comparingInt(Materia::getPriority).reversed()
        );

        sessionMaterias = new LinkedList<>(materias);
    }

    public static final ViewModelInitializer<SessionViewModel> initializer = new ViewModelInitializer<>(SessionViewModel.class,
            creationExtras  -> {
                ChronoFocusApp app = (ChronoFocusApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                ChronoDataBase db = ChronoDataBase.getInstance(app);
                SessionTimerRepository _timerRepo = app.getsRepo();
                // DISCLAIMER: Provisório enquanto não tem a factory ainda
                MateriaRepository _materiaRepo = app.getRepo();
                return new SessionViewModel(_timerRepo, _materiaRepo);
            }
    );

    private LiveData<List<Materia>> getOrderedDayMaterias(DaysWeek day) {

        // -> return materiaRepo.listarMateriasOrdenadas(day)
        return materiaRepo.listarMaterias(day);
    }

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
    // SessionViewModel.GetNextMateria(),
    // se conseguir, prossegue, se não, finish().
    // GetNextMateria() chama SessionManager
    //
    // Materia materia = SessionViewModel.GetNextMateria()
    // time = materia.getBaseTime();

    public Materia getNextMateria() {
        return sessionMaterias.poll();
    }
}
