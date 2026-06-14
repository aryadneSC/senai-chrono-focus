package com.example.chronofocus.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

@Entity
public class SessionTimer {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "session_id")
    private int sessionID;
    @ColumnInfo(name = "materia_id")
    private int materiaID;
    @ColumnInfo(name = "base_time")
    private long baseTime;
    @ColumnInfo(name = "status")
    private Status status;

    @ColumnInfo(name = "ended_at")
    private long endedAt;

    @ColumnInfo(name = "started_at")
    private long startedAt = -1;

    public SessionTimer(int materiaID, long baseTime, Status status) {
        this.materiaID = materiaID;
        this.baseTime = baseTime;
        this.status = status;
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

    public long getBaseTime() {
        return baseTime;
    }

    public int getMateriaID() {
        return materiaID;
    }

    public long getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(long endedAt) {
        this.endedAt = endedAt;
    }
    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public void setMateriaID(int materiaID) {
        this.materiaID = materiaID;
    }

    public void setBaseTime(long baseTime) {
        this.baseTime = baseTime;
    }

}
