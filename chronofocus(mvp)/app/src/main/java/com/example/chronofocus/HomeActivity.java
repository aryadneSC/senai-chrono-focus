package com.example.chronofocus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button btnIniciarSessao;
    private TextView txtVerTodas, txtSaudacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnIniciarSessao = findViewById(R.id.btnIniciarSessao);
        txtVerTodas = findViewById(R.id.txtVerTodas);
        txtSaudacao = findViewById(R.id.txtSaudacao);

        SharedPreferences prefs = getSharedPreferences("ChronoPrefs", Context.MODE_PRIVATE);
        String nomeSalvo = prefs.getString("usuario_nome", "Usuário");

        txtSaudacao.setText(String.format("Olá, %s!", nomeSalvo));

        txtVerTodas.setOnClickListener(v -> {
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
