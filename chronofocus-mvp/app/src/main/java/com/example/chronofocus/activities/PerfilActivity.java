package com.example.chronofocus.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivityPerfilBinding;

public class PerfilActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPerfilBinding binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mostra nome salvo no login
        SharedPreferences chronoPrefs = getSharedPreferences("ChronoPrefs", Context.MODE_PRIVATE);
        String nome = chronoPrefs.getString("usuario_nome", "Usuário");
        binding.tvNomeUsuario.setText(nome);

        // Controle de tema
        SharedPreferences themePrefs = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int currentTheme = themePrefs.getInt("current_theme", 0);
        binding.btnAlternarTema.setText(currentTheme == 1 ? R.string.tema_mudar_para_mono : R.string.tema_mudar_para_lilas);

        binding.btnAlternarTema.setOnClickListener(v -> {
            int novoTema = (themePrefs.getInt("current_theme", 0) == 1) ? 0 : 1;
            themePrefs.edit().putInt("current_theme", novoTema).apply();
            recreate();
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }
}
