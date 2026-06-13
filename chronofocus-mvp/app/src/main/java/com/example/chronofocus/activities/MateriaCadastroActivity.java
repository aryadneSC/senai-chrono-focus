package com.example.chronofocus.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivityMateriaCadastroBinding;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.viewmodels.MateriaCadastroViewModel;

import java.util.List;


public class MateriaCadastroActivity extends AppCompatActivity {

    //utilizar binding.id_componente. em vez de FindViewById
    private ActivityMateriaCadastroBinding  binding;
    private MateriaCadastroViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMateriaCadastroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(MateriaCadastroViewModel.initializer)).get(MateriaCadastroViewModel.class);
        EdgeToEdge.enable(this);
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myAdapter(DaysWeek.values());
        binding.btn2.setOnClickListener(v -> {
            String nome = binding.txtbx1.getText().toString();
            DaysWeek dia = (DaysWeek) binding.spinner.getSelectedItem();
            if (nome.isEmpty() | dia == null){
                Toast.makeText(this, "Por favor, digite o nome da materia!", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.inserirMateria(new Materia(nome, 30, dia, 5));
                finish();
            }
        });


    }

    private void myAdapter(DaysWeek[] list){
        ArrayAdapter<DaysWeek> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        binding.spinner.setAdapter(adapter);
    }
}