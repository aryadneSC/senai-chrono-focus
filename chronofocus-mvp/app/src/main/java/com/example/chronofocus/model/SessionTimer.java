package com.example.chronofocus.model;

public class SessionTimer {
    private final int materiaID;

    private long baseMillis;
    private long pauseRemaining = -1;
    private long endTimeMillis = -1;
    private SessionStatus sessionStatus;

    public SessionTimer(int materiaID, long baseMillis, SessionStatus sessionStatus) {
        this.materiaID = materiaID;
        this.baseMillis = baseMillis;
        this.sessionStatus = sessionStatus;
    }

    public long getEndTimeMillis() {
        return endTimeMillis;
    }

    public SessionStatus getStatus() {
        return sessionStatus;
    }

    public void setStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public long getBaseMillis() {
        return baseMillis;
    }

    public int getMateriaID() {
        return materiaID;
    }

    public void setEndTimeMillis(long endTimeMillis) {
        this.endTimeMillis = endTimeMillis;
    }
    public void setBaseMillis(long baseMillis) {
        this.baseMillis = baseMillis;
    }

    public long getPauseRemaining() {
        return pauseRemaining;
    }

    public void setPauseRemaining(long pauseRemaining) {
        this.pauseRemaining = pauseRemaining;
        sessionStatus = SessionStatus.PAUSED;
    }
}
