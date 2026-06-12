package com.example.chronofocus.manager;

import androidx.annotation.Nullable;

import com.example.chronofocus.model.Materia;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private ArrayList<Materia> materiasDoDia;
    int limit = 0;

    public SessionManager(int limit){
        materiasDoDia = new ArrayList<>();
        this.limit = limit;
    }

    public void addMateriasDoDia(ArrayList<Materia> list){
        materiasDoDia.addAll(list);
    }

    @Nullable
    public Materia getMateria(){
        if (!materiasDoDia.isEmpty())
            return new Materia(materiasDoDia.get(0));
        return null;
    }

    @Nullable
    public Materia getMateria(int index){

        if (index < materiasDoDia.size() && index >= 0)
            return new Materia(materiasDoDia.get(index));

        return null;
    }


   public void removeMateria(){
        if (materiasDoDia.isEmpty())
            return;
         materiasDoDia.remove(0);
   }


   public  void removeMateria(int index){
        if (index >= materiasDoDia.size() || index < 0)
            return;
        materiasDoDia.remove(index);
   }

    @Nullable
   public  List<Materia> getMateriasDoDia(){
        return List.copyOf(materiasDoDia);
   }


}
