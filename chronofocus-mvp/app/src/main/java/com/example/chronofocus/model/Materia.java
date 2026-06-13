package com.example.chronofocus.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Locale;

@Entity(indices = {@Index(value = {"nome"}, unique = true)})
public class Materia  {
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
	private int id;
    @ColumnInfo(name  = "nome")
	private String nome;
	@ColumnInfo(name = "base_time")
	private long baseTime;
	@ColumnInfo(name = "day")
	private DaysWeek day;
	@ColumnInfo(name = "priority")
	private int priority;
	@ColumnInfo(name = "ultimo_dia_estudado")
	private String ultimoDiaEstudado;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setBaseTime(long baseTime) {
		this.baseTime = baseTime;
	}

	public void setDay(DaysWeek day) {
		this.day = day;
	}

	public DaysWeek getDay() {return day;}

	public String getUltimoDiaEstudado() {
		return ultimoDiaEstudado;
	}

	public void setUltimoDiaEstudado(String ultimoDiaEstudado) {
		this.ultimoDiaEstudado = ultimoDiaEstudado;
	}

	@NonNull
    @Override
	public String toString() {

		return  String.format(Locale.getDefault(),"%s", nome);
	}
}
