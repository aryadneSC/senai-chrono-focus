package com.example.chronofocus.activities;

import static android.app.PendingIntent.getActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

       binding.listVMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Materia materia = materias.getValue().get(position);
                deleteButton(materia.getId());
            }
        });


    }

   private void myAdapter(List<Materia> list){
        ArrayAdapter<Materia> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        binding.listVMaterias.setAdapter(adapter);
   }

   private void deleteButton(int materiaId){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.msg_dlt_materia).setTitle(R.string.deletar_materia);
        builder.setPositiveButton(R.string.sim_msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                 viewModel.deletarMateria(materiaId);
            }
        });
       builder.setNegativeButton(R.string.nao_msg, null).show();


   }

}