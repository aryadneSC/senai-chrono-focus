package com.example.chronofocus;

import android.app.Application;

import com.example.chronofocus.data.ChronoDataBase;
import com.example.chronofocus.repository.MateriaRepository;
import com.example.chronofocus.repository.SessionTimerRepository;


public class ChronoFocusApp extends Application {
   private  ChronoDataBase db;
   private MateriaRepository repo;
   private SessionTimerRepository sRepo;
   @Override
    public void onCreate(){
       super.onCreate();
       db = ChronoDataBase.getInstance(getApplicationContext());
       repo = new MateriaRepository(db.materiaDao());
       sRepo = new SessionTimerRepository(db.sessionTimerDao());
   }

    public MateriaRepository getRepo() {
        return repo;
    }
    public ChronoDataBase getDb() {
        return db;
    }

    public SessionTimerRepository getsRepo() {
        return sRepo;
    }

}
