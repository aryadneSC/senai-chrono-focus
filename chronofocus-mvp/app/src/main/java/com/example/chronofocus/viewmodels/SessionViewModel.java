package com.example.chronofocus.viewmodels;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.os.SystemClock;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.chronofocus.ChronoFocusApp;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.model.SessionStatus;
import com.example.chronofocus.repository.MateriaRepository;
import com.example.chronofocus.repository.SessionRepository;
import com.example.chronofocus.utils.DataUtils;
import com.example.chronofocus.utils.ThreadsManager;
import com.example.chronofocus.utils.TimerUtils;

import java.util.LinkedList;

public class SessionViewModel extends ViewModel {

    public enum SessionState {
        LOADING,
        SUBJECTS_READY,
        NO_SUBJECTS_FOUND
    }
    private final SessionRepository sessionRepo;
    private LinkedList<Materia> queue;
    // SessionTimer poderia ser mutable pra salvar automaticamente com observer, mas não temos mais tempo..
    private SessionTimer sessionTimer;

    private final MutableLiveData<SessionState> state = new MutableLiveData<>(SessionState.LOADING);
    private final MutableLiveData<String> currentMateriaName = new MutableLiveData<>();
    private final MutableLiveData<String> nextMateriaName = new MutableLiveData<>();
    private final MutableLiveData<String> formattedPausedOrInactive = new MutableLiveData<>();

    public LiveData<SessionState> getState() { return state; }
    public LiveData<String> getCurrentMateriaName() { return currentMateriaName; }
    public LiveData<String> getNextMateriaName() { return nextMateriaName; }
    public LiveData<String> getFormattedPausedOrInactive() { return formattedPausedOrInactive; }

    public long getBaseMillis() {
        return sessionTimer.getBaseMillis();
    }

    public long updateAndGetEndTimeMillis() {
        if (sessionTimer.getPauseRemaining() != -1) {
            long endTime = SystemClock.elapsedRealtime() + sessionTimer.getPauseRemaining();
            sessionTimer.setEndTimeMillis(endTime);
            sessionRepo.saveSession(sessionTimer);
            return endTime;
        }

        if (sessionTimer.getEndTimeMillis() != -1) {
            return sessionTimer.getEndTimeMillis();
        }

        long endTime = SystemClock.elapsedRealtime() + sessionTimer.getBaseMillis();
        sessionTimer.setEndTimeMillis(endTime);
        return endTime;
    }

    public void notifyStarted() {
        sessionTimer.setPauseRemaining(-1);
        sessionTimer.setStatus(SessionStatus.STARTED);
        sessionRepo.saveSession(sessionTimer);
    }

    public void notifyPaused(boolean quietly) {
        long remaining = sessionTimer.getEndTimeMillis() - SystemClock.elapsedRealtime();
        sessionTimer.setPauseRemaining(remaining);

        if(!quietly) {
            updatePausedOrInactive(remaining);
        }
        sessionTimer.setStatus(SessionStatus.PAUSED);
        sessionRepo.saveSessionBlocking(sessionTimer);
    }

    private void updatePausedOrInactive(long remaining) {
        formattedPausedOrInactive.postValue(TimerUtils.millisToFormattedTimeString(remaining, getBaseMillis()));
    }

    public void notifyEnded(Runnable onCompleted) {
        sessionTimer.setPauseRemaining(-1);
        sessionTimer.setEndTimeMillis(-1);
        sessionTimer.setStatus(SessionStatus.FINISHED);
        queue.poll();

        ThreadsManager.startTask(() -> {
            sessionRepo.updateUltimoDiaEstudado(sessionTimer.getMateriaID(), DataUtils.returnActualDate());
            sessionRepo.clearSession();
            if (onCompleted != null) onCompleted.run();
        });
    }
    public void pauseAndSave(Runnable onCompleted) {
        long remaining = sessionTimer.getEndTimeMillis() - SystemClock.elapsedRealtime();
        sessionTimer.setPauseRemaining(remaining);
        sessionTimer.setStatus(SessionStatus.PAUSED);

        ThreadsManager.startTask(() -> {
            sessionRepo.saveSessionBlocking(sessionTimer);
            if (onCompleted != null) onCompleted.run();
        });
    }

    public void notifyNext() {
        ThreadsManager.startTask(() -> {
            sessionTimer.setStatus(SessionStatus.INACTIVE);
            var materia = queue.peek();

            if (materia == null) {
                state.postValue(SessionState.NO_SUBJECTS_FOUND);
                return;
            }

            startFreshSession(materia);
            syncPausedOrInactive();
            sessionRepo.saveSession(sessionTimer);
            state.postValue(SessionState.SUBJECTS_READY);
        });
    }

    public void resetSession() {
        if (sessionTimer == null) return;

        sessionTimer.setPauseRemaining(-1);
        sessionTimer.setEndTimeMillis(-1);
        sessionRepo.updateUltimoDiaEstudado(sessionTimer.getMateriaID(), null);
    }

    private void updateCurrentAndNextMateriaNames(Materia materia) {
        currentMateriaName.postValue(materia.getNome());
        nextMateriaName.postValue(queue.size() > 1 ? queue.get(1).getNome() : "");
    }
    private void startFreshSession(Materia materia) {
        sessionTimer = new SessionTimer(materia.getId(), materia.getBaseTime(), SessionStatus.INACTIVE);
        updateCurrentAndNextMateriaNames(materia);
    }

    private void syncPausedOrInactive() {
        if(sessionTimer == null) return;

        var status = sessionTimer.getStatus();
        if (status == SessionStatus.PAUSED || status == SessionStatus.INACTIVE) {
            long baseTime = sessionTimer.getBaseMillis();
            long pauseTime = sessionTimer.getPauseRemaining();

            long remaining = pauseTime != -1 ? pauseTime : baseTime;
            updatePausedOrInactive(remaining);
        }
    }
    private void setupSession() {
        state.postValue(SessionState.LOADING);

        SessionState newState = SessionState.NO_SUBJECTS_FOUND;

        if (queue != null) {
            var materia = queue.peek();
            if (materia != null) {
                if (sessionRepo.hasSession()) {
                    SessionTimer restored = sessionRepo.loadSession();
                    if (restored != null && restored.getMateriaID() == (materia.getId())) {
                        sessionTimer = restored;
                        updateCurrentAndNextMateriaNames(materia);
                    } else {
                        sessionRepo.clearSessionBlocking();
                        startFreshSession(materia);
                    }
                } else {
                    startFreshSession(materia);
                }

                if(sessionTimer != null) {
                    newState = SessionState.SUBJECTS_READY;
                }

                syncPausedOrInactive();
            }
        }
        state.postValue(newState);
    }

    public void updateSessionData() {
        ThreadsManager.startTask(() -> {
            state.postValue(SessionState.LOADING);
            queue = sessionRepo.getSessionSequence(DaysWeek.getCurrentDay().name(), DataUtils.returnActualDate());
            setupSession();
        });
    }

    public SessionViewModel(MateriaRepository materiaRepo, SessionRepository sessionRepo) {
        this.sessionRepo = sessionRepo;
        queue = new LinkedList<>();
    }

    public static final ViewModelInitializer<SessionViewModel> initializer = new ViewModelInitializer<>(
            SessionViewModel.class,
            creationExtras -> {
                ChronoFocusApp app = (ChronoFocusApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new SessionViewModel(app.getRepo(), app.getSessionRepository());
            }
    );
}