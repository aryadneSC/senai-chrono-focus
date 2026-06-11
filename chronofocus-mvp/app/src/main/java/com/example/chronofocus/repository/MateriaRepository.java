package com.example.chronofocus.repository;

import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MateriaRepository  {
    private final List<Materia> listaDeMaterias = new ArrayList<>();
    public List<Materia> getListaMateria(){
        return List.copyOf(listaDeMaterias);
    }
    public void add(Materia materia){
        listaDeMaterias.add(materia);
    }

    public void remove(Materia materia){
        listaDeMaterias.remove(materia);
    }

    public void remove(int index){
        listaDeMaterias.remove(index);
    }

    public void removeAll(){
        listaDeMaterias.clear();
    }
    public List<Materia> filtrarMateria(DaysWeek day, int limit){
        return listaDeMaterias.stream().filter( x -> x.getDay() == day).limit(limit).collect(Collectors.toList());
    }

    public List<Materia> filtrarMateria(int limit){
        return listaDeMaterias.stream().limit(limit).collect(Collectors.toList());
    }
}
