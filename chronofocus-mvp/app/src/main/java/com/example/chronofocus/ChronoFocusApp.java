package com.example.chronofocus;

import android.app.Application;

import com.example.chronofocus.data.ChronoDataBase;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.repository.MateriaRepository;
import com.example.chronofocus.repository.SessionRepository;
import com.example.chronofocus.repository.SessionTimerRepository;
import com.example.chronofocus.utils.DataUtils;


public class ChronoFocusApp extends Application {
   private  ChronoDataBase db;
   private MateriaRepository materiaRepo;
   private SessionTimerRepository timerRepo;
   private SessionRepository sessionRepo;
   @Override
    public void onCreate(){
       super.onCreate();
       db = ChronoDataBase.getInstance(getApplicationContext());
       materiaRepo = new MateriaRepository(db.materiaDao());
       timerRepo = new SessionTimerRepository();
       sessionRepo = new SessionRepository(db.materiaDao());
   }

    public MateriaRepository getRepo() {
        return materiaRepo;
    }
    public ChronoDataBase getDb() {
        return db;
    }

    public SessionTimerRepository getTimerRepo() {
        return timerRepo;
    }

    public SessionRepository getSessionRepository(){ return sessionRepo; }

}
