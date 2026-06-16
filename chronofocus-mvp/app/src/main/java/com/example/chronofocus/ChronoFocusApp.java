package com.example.chronofocus;

import android.app.Application;

import com.example.chronofocus.data.ChronoDataBase;
import com.example.chronofocus.repository.MateriaRepository;
import com.example.chronofocus.repository.SessionRepository;


public class ChronoFocusApp extends Application {
   private  ChronoDataBase db;
   private MateriaRepository materiaRepo;
   private SessionRepository sessionRepo;
   @Override
    public void onCreate(){
       super.onCreate();
       db = ChronoDataBase.getInstance(getApplicationContext());
       materiaRepo = new MateriaRepository(db.materiaDao());
       sessionRepo = new SessionRepository(this, db.materiaDao());
   }

    public MateriaRepository getRepo() {
        return materiaRepo;
    }
    public ChronoDataBase getDb() {
        return db;
    }

    public SessionRepository getSessionRepository(){ return sessionRepo; }

}
