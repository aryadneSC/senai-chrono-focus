package com.example.chronofocus.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronofocus.databinding.ActivityHomeBinding;
import com.example.chronofocus.model.Materia;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private TextView txtVerTodas, txtSaudacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        SharedPreferences prefs = getSharedPreferences("ChronoPrefs", Context.MODE_PRIVATE);
        String nomeSalvo = prefs.getString("usuario_nome", "Usuário");

        binding.txtSaudacao.setText(String.format("Olá, %s!", nomeSalvo));

        binding.txtVerTodas.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SubjectListActivity.class);
            startActivity(intent);
        });


        binding.btnIniciarSessao.setOnClickListener(v -> {
             Intent intent = new Intent(HomeActivity.this, TimerActivity.class);
             startActivity(intent);
        });

    }

    private void myAdapter(List<Materia> list){
        ArrayAdapter<Materia> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        binding..setAdapter(adapter);
    }
}
