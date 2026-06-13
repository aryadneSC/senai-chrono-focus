package com.example.chronofocus.repository;

import com.example.chronofocus.data.SessionTimerDao;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.utils.ThreadsManager;

public class SessionTimerRepository {
    private final SessionTimerDao dao; //data access object

    public SessionTimerRepository(SessionTimerDao dao) {
        this.dao = dao;
    }

    public void insertSessionTimer(SessionTimer sessionTimer){
        ThreadsManager.startTask(new Runnable() {
            @Override
            public void run(){
                dao.insertSessionTimer(sessionTimer);
            }
        });
    }

    public void deleteSessionTimer(SessionTimer sessionTimer){
        ThreadsManager.startTask(new Runnable() {
            @Override
            public void run(){
                dao.deleteSessionTimer(sessionTimer);
            }
        });
    }
}
