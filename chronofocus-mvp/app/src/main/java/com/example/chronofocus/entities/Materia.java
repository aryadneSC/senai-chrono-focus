package com.example.chronofocus.entities;

import java.io.Serializable;

public class Materia implements Serializable {
	private String nome;
	private long baseTime;
	
	public Materia(String nome, long baseTime) {
		this.nome = nome;
		this.baseTime = baseTime;
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
	
	@Override
	public String toString() {
		String format = String.format("%s\n%d", nome, baseTime);
		return format;
	}
}
