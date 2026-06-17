package com.example.chronofocus.data;

import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;

import java.util.ArrayList;

public class MateriaFireBaseData {
    private int id;
    private String nome;
    private long baseTime;
    private ArrayList<DaysWeek> day;
    private String ultimoDiaEstudado;

    public MateriaFireBaseData() {}

    public MateriaFireBaseData(Materia materia){
        this.id = materia.getId();
        this.nome = materia.getNome();
        this.day = materia.getDay();
        this.baseTime = materia.getBaseTime();
        this.ultimoDiaEstudado = materia.getUltimoDiaEstudado();
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

    public ArrayList<DaysWeek> getDay() {
        return day;
    }

    public void setDay(ArrayList<DaysWeek> day) {
        this.day = day;
    }

    public String getUltimoDiaEstudado() {
        return ultimoDiaEstudado;
    }

    public void setUltimoDiaEstudado(String ultimoDiaEstudado) {
        this.ultimoDiaEstudado = ultimoDiaEstudado;
    }
}