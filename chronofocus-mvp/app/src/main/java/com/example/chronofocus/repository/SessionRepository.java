package com.example.chronofocus.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.chronofocus.ChronoFocusApp;
import com.example.chronofocus.data.MateriaDao;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionStatus;
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.utils.DataUtils;
import com.example.chronofocus.utils.ThreadsManager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class SessionRepository {
    private Queue<Materia> sessionSequence;
    private LiveData<List<Materia>> materiasDoDia;
    private MateriaDao materiaDao;
    private SharedPreferences prefs;

    private final String PREF_NAME = "session_prefs";
    private static final String KEY_MATERIA_ID = "materia_id";
    private static final String KEY_BASE_MILLIS = "base_millis";
    private static final String KEY_PAUSE_REMAINING = "pause_remaining";

    private static final String KEY_END_TIME_MILLIS = "end_time_milis";
    private static final String KEY_STATUS = "status";

    public SessionRepository(ChronoFocusApp context, MateriaDao materiaDao){
        if (materiaDao == null)
            throw new NullPointerException("Materia dao não pode ser nulo");
        this.materiaDao = materiaDao;

        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public void saveSession(SessionTimer sessionTimer) {
        prefs.edit()
                .putInt(KEY_MATERIA_ID, sessionTimer.getMateriaID())
                .putLong(KEY_BASE_MILLIS, sessionTimer.getBaseMillis())
                .putLong(KEY_PAUSE_REMAINING, sessionTimer.getPauseRemaining())
                .putLong(KEY_END_TIME_MILLIS, sessionTimer.getEndTimeMillis())
                .putString(KEY_STATUS, sessionTimer.getStatus().name())
                .apply();
    }
    public void saveSessionBlocking(SessionTimer sessionTimer) {
        prefs.edit()
                .putInt(KEY_MATERIA_ID, sessionTimer.getMateriaID())
                .putLong(KEY_BASE_MILLIS, sessionTimer.getBaseMillis())
                .putLong(KEY_PAUSE_REMAINING, sessionTimer.getPauseRemaining())
                .putLong(KEY_END_TIME_MILLIS, sessionTimer.getEndTimeMillis())
                .putString(KEY_STATUS, sessionTimer.getStatus().name())
                .commit();
    }

    public SessionTimer loadSession() {
        int materiaID = prefs.getInt(KEY_MATERIA_ID, -1);
        if (materiaID == -1) return null;
        long baseMillis = prefs.getLong(KEY_BASE_MILLIS, 0);
        long pauseRemaining = prefs.getLong(KEY_PAUSE_REMAINING, -1);
        long endTimeMillis = prefs.getLong(KEY_END_TIME_MILLIS, -1);
        SessionStatus status = SessionStatus.valueOf(prefs.getString(KEY_STATUS, SessionStatus.INACTIVE.name()));

        SessionTimer sessionTimer = new SessionTimer(materiaID, baseMillis, status);
        sessionTimer.setPauseRemaining(pauseRemaining);
        sessionTimer.setEndTimeMillis(endTimeMillis);
        return sessionTimer;
    }
    public void clearSession() {
        prefs.edit().clear().apply();
    }

    public void clearSessionBlocking() {
        prefs.edit().clear().commit();
    }
    public boolean hasSession() {
        return prefs.getInt(KEY_MATERIA_ID, -1) != -1;
    }
    public void carregarMateriasDoDia(String currentDay, String currentData){
        materiasDoDia = materiaDao.listarMaterias("%" + currentDay + "%", currentData);
    }
    public void updateUltimoDiaEstudado(int id, String date){
        ThreadsManager.startTask(new Runnable() {
            @Override
            public void run() {
                materiaDao.updateMateria(id, date);
            }
        });
    }

    public LiveData<List<Materia>> getMateriasDoDia(){
        return materiasDoDia;
    }


    public void hasSessionOn(String day, Consumer<Boolean> callback) {
        ThreadsManager.startTask(() -> {
            boolean result = materiaDao.countMateriasOn(day) > 0;
            callback.accept(result);
        });
    }
    public LinkedList<Materia> getSessionSequence(String day, String date) {
        List<Materia> result = materiaDao.listMateriasForSessionSync(day, date);
        return new LinkedList<>(result != null ? result : Collections.emptyList());
    }

}