package com.example.chronofocus.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"nome"}, unique = true)})
public class Materia  {
   @PrimaryKey(autoGenerate = true)
	private int id;

    @ColumnInfo(name  = "nome")
	private String nome;
	@ColumnInfo(name = "base_time")
	private long baseTime;
	@ColumnInfo(name = "day")
	private DaysWeek day;
	@ColumnInfo(name = "priority")
	private int priority;
	public Materia(String nome, long baseTime, DaysWeek day, int priority){
		this.nome = nome;
		this.baseTime = baseTime;
		this.day = day;
	}

	public Materia(Materia materia){
		this.nome = materia.nome;
		this.baseTime = materia.baseTime;
		this.priority = materia.priority;
		this.day = materia.day;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getBaseTime() {
		return baseTime;
	}

	public void setBaseTime(long baseTime) {
		this.baseTime = baseTime;
	}

	public DaysWeek getDay() {return day;}

	@Override
	public String toString() {
		String format = String.format("%s\n%d", nome, baseTime);
		return format;
	}
}
