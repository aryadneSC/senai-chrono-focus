package com.example.chronofocus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivitySubjectListBinding;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.viewmodels.SubjectListViewModel;

import java.util.List;

public class SubjectListActivity extends AppCompatActivity {

    private SubjectListViewModel viewModel;
    private ActivitySubjectListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySubjectListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(SubjectListViewModel.initializer)).get(SubjectListViewModel.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        binding.btnVoltarMateria.setOnClickListener(v -> finish());
        binding.button2.setOnClickListener(v -> {
            Intent cadastroTela = new Intent(this, MateriaCadastroActivity.class);
            startActivity(cadastroTela);


        });
        LiveData<List<Materia>> materias = viewModel.getAllMateria();

       materias.observe(this, o -> {
           myAdapter(materias.getValue());
       });



    }

   private void myAdapter(List<Materia> list){
        ArrayAdapter<Materia> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        binding.listVMaterias.setAdapter(adapter);
   }


}