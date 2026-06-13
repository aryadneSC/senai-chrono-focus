package com.example.chronofocus.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SessionTimer {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "session_id")
    private int sessionID;
    @ColumnInfo(name = "current_materia_id")
    private int currentMateriaID;
    @ColumnInfo(name = "current_time")
    private long currentTime;
    @ColumnInfo(name = "status")
    private Status status;
    @ColumnInfo(name = "start_time_stamp")
    private long startTimestamp;
    @ColumnInfo(name = "end_time_stamp")
    private long endTimestamp = -1;

    public SessionTimer(int currentMateriaID, long currentTime, long startTimestamp, Status status) {
        this.currentMateriaID = currentMateriaID;
        this.currentTime = currentTime;
        this.startTimestamp = startTimestamp;
        this.status = status;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public int getCurrentMateriaID() {
        return currentMateriaID;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public void setCurrentMateriaID(int currentMateriaID) {
        this.currentMateriaID = currentMateriaID;
    }

    @TypeConverter //para converter o enum pra room
    public static Status fromString(String value) {
        return Status.valueOf(value);
    }

    @TypeConverter
    public static String fromStatus(Status status) {
        return status.name();
    }
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
}
