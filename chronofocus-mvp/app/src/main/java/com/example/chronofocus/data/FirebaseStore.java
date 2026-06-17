package com.example.chronofocus.data;

import android.util.Log;
import android.widget.Toast;
import com.example.chronofocus.model.Materia;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FirebaseStore {


    public static void add(Materia materia){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference materias = db.collection("materias");
        if(materia != null){
            materias.add(materia).addOnSuccessListener(v ->{
                Log.d("Firebase", "materia adicionada!");

            }).addOnFailureListener( o -> {
                Log.e("Firebase", "materia não adicionada" + o.getMessage());
            });
            }
    }

    public static List<Materia> load(){
        ArrayList<Materia> materias = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("materias").get().addOnSuccessListener(v -> {
           for(QueryDocumentSnapshot doc : v){
               materias.add(doc.toObject(Materia.class));
           }
        });
        return List.copyOf(materias);
    }

}
