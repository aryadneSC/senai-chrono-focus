package com.example.chronofocus.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {



    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("ChronoPrefs", Context.MODE_PRIVATE);
        if(prefs.contains("usuario_nome"))
            nextView();


        binding.btnLogin.setOnClickListener(v -> {
            String nome = binding.edtNomeUsuario.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(this, "Por favor, digite seu nome!", Toast.LENGTH_SHORT).show();
            } else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("usuario_nome", nome);
                    editor.apply();
                    nextView();
            }
        });
        }

        private void nextView(){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }