package com.example.chronofocus.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivityMateriaCadastroBinding;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.utils.DataUtils;
import com.example.chronofocus.viewmodels.MateriaCadastroViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
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

        chipSetText(DaysWeek.values(), chipExtractor(binding.chipGroupDias));

        binding.btn2.setOnClickListener(v -> {
            String nome = binding.txtbx1.getText().toString();

            ArrayList<DaysWeek> dia = chipTextExtractor(chipSelectedExtractor());
            Toast.makeText(this, String.format("ARRAY %s", dia.toString()), Toast.LENGTH_LONG).show();

            if (nome.isEmpty() | dia.isEmpty()){
                Toast.makeText(this, "Por favor, digite o nome da materia!", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.inserirMateria(new Materia(nome, 30, dia, 5));
                finish();
            }
        });


    }


    private ArrayList<DaysWeek> chipTextExtractor(List<Chip> chips){
        ArrayList<DaysWeek> days = new ArrayList<>();
        DaysWeek day;
        for (Chip c : chips){
            day = DaysWeek.stringToEnum(c.getText());
            if (day != null)
                days.add(day);
        }
        return days;
    }

    private List<Chip> chipSelectedExtractor(){
        ChipGroup chipGroup = binding.chipGroupDias;
        ArrayList<Chip> chips = new ArrayList<>();
        List<Integer> ids = chipGroup.getCheckedChipIds();
        for(int id : ids){
            chips.add(chipGroup.findViewById(id));
        }
        return  chips;
    }

    private List<Chip> chipExtractor(ChipGroup chipGroup){
        View objExtracted;
        int chipGroupLength =  chipGroup.getChildCount();
        ArrayList<Chip> chipList = new ArrayList<>();

        for (int i = 0; i < chipGroupLength; i++){
            objExtracted = chipGroup.getChildAt(i);
            if (objExtracted instanceof Chip){
                chipList.add((Chip) objExtracted);
            }
        }

       return chipList;
    }


    private void chipSetText(DaysWeek[] days, List<Chip> chipList){
        if(chipList == null || chipList.isEmpty() || chipList.size() < days.length)
            return;
        for(int i = 0; i < days.length; i++){
            chipList.get(i).setText(days[i].toString());
        }
    }
}