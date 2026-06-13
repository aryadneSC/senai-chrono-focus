package com.example.chronofocus.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.chronofocus.databinding.ActivityHomeBinding;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.utils.DataUtils;
import com.example.chronofocus.viewmodels.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private TextView txtVerTodas, txtSaudacao;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(HomeViewModel.inicializer)).get(HomeViewModel.class);
        recuperarNomeUsuario();
        viewModel.addMateriasDoDia(DaysWeek.getCurrentDay());
        Toast.makeText(this, String.format("hoje é %s data: %s", DaysWeek.getCurrentDay(), DataUtils.returnActualDate()), Toast.LENGTH_LONG).show();

        LiveData<List<Materia>> materias = viewModel.getMateriasDoDia();
        materias.observe(this, o ->{
                myAdapter(materias.getValue());
        });

        binding.txtVerTodas.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SubjectListActivity.class);
            startActivity(intent);
        });

        binding.btnIniciarSessao.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SessionActivity.class);
            startActivity(intent);
        });

    }

    private void recuperarNomeUsuario(){
        SharedPreferences prefs = getSharedPreferences("ChronoPrefs", Context.MODE_PRIVATE);
        String nomeSalvo = prefs.getString("usuario_nome", "Usuário");
        binding.txtSaudacao.setText(String.format("Olá, %s!", nomeSalvo));
    }


    private void myAdapter(List<Materia> materias){

        ArrayAdapter<Materia> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, materias);
        binding.listView.setAdapter(adapter);

    }
}
