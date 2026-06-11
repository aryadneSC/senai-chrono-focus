package com.example.chronofocus.model;

import java.io.Serializable;

public class Materia implements Serializable {
	private String nome;
	private long baseTime;
	private DaysWeek day;
	private int priority;
	public Materia(String nome, long baseTime, DaysWeek day, int priority){
		this.nome = nome;
		this.baseTime = baseTime;
		this.day = day;
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
