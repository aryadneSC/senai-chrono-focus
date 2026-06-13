package com.example.chronofocus.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionTimer;

import java.util.List;

@Dao
public interface SessionTimerDao {
    @Insert
    public void insertSessionTimer(SessionTimer sessionTimer);
    @Delete
    public void deleteSessionTimer(SessionTimer sessionTimer);
    @Query("SELECT * FROM SessionTimer WHERE start_time_stamp BETWEEN :dayStart AND :dayEnd LIMIT 1")
    public SessionTimer getSessionTimer(long dayStart, long dayEnd);
}
