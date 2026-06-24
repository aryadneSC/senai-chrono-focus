package com.example.chronofocus.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.example.chronofocus.databinding.ActivityHomeBinding;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.utils.DataUtils;
import com.example.chronofocus.utils.MateriaAdapter;
import com.example.chronofocus.viewmodels.HomeViewModel;

import java.util.List;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        HomeViewModel viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(HomeViewModel.inicializer)).get(HomeViewModel.class);
        recuperarNomeUsuario();
        viewModel.addMateriasDoDia(DaysWeek.getCurrentDay().name(), DataUtils.returnActualDate());

        viewModel.getMateriasDoDia().observe(this, list -> {
            MateriaAdapter adapter = new MateriaAdapter(this, list);
            binding.listView.setAdapter(adapter);
        });

        binding.txtVerTodas.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SubjectListActivity.class);
            startActivity(intent);
        });

        binding.btnIniciarSessao.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SessionActivity.class);
            startActivity(intent);
        });

        binding.btnIrPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PerfilActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    private void recuperarNomeUsuario(){
        SharedPreferences prefs = getSharedPreferences("ChronoPrefs", Context.MODE_PRIVATE);
        String nomeSalvo = prefs.getString("usuario_nome", "Usuário");
        binding.txtSaudacao.setText(String.format("Olá, %s!", nomeSalvo));
    }
}