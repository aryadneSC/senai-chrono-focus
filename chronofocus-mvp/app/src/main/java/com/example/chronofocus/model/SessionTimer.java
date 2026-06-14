package com.example.chronofocus.model;

public class SessionTimer {
    private int materiaID;
    private long time;
    private Status status;
    private long startedAt = -1;

    public SessionTimer(int materiaID, long time, Status status) {
        this.materiaID = materiaID;
        this.time = time;
        this.status = status;
    }

    public SessionTimer(SessionTimer sessionTimer) {
        this.materiaID = sessionTimer.materiaID;
        this.time = sessionTimer.time;
        this.status = sessionTimer.status;
        this.startedAt = sessionTimer.startedAt;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public int getMateriaID() {
        return materiaID;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

}
