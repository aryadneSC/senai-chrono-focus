package com.example.chronofocus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText edtNomeUsuario;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNomeUsuario = findViewById(R.id.edtNomeUsuario);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String nome = edtNomeUsuario.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, digite seu nome!", Toast.LENGTH_SHORT).show();
            } else {

                SharedPreferences prefs = getSharedPreferences("ChronoPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("usuario_nome", nome);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        }
    }