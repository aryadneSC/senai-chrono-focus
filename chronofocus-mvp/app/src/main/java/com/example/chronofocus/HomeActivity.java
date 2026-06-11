package com.example.chronofocus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronofocus.databinding.ActivityHomeBinding;

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
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Quando add... Abre Timer
        /*
        btnIniciarSessao.setOnClickListener(v -> {
            // Intent intent = new Intent(HomeActivity.this, ExemploTrocarPraTimer.class);
            // startActivity(intent);
        });
        */
    }
}
