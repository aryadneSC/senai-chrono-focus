package com.example.chronofocus.activities;

import static com.example.chronofocus.utils.TimerUtils.getMillisFromTimes;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivityMateriaCadastroBinding;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionStatus;
import com.example.chronofocus.utils.ThemeUtils;
import com.example.chronofocus.viewmodels.MateriaCadastroViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MateriaCadastroActivity extends BaseActivity {

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

        binding.btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        setupNumericUpDown();

        chipSetText(DaysWeek.values(), chipExtractor(binding.chipGroupDias));
        binding.btnSalvar.setOnClickListener(v -> {
            String nome = binding.etNome.getText() != null ? binding.etNome.getText().toString() : "";

            ArrayList<DaysWeek> dia = chipTextExtractor(chipSelectedExtractor());

            long baseTime = getMillisFromTimes(
                    getNumericValue(binding.etHour),
                    getNumericValue(binding.etMinute),
                    getNumericValue(binding.etSecond));

            if (nome.isEmpty()){
                Toast.makeText(this, "Por favor, digite o nome da materia!", Toast.LENGTH_LONG).show();
            } else if (dia.isEmpty()) {
                Toast.makeText(this, "Por favor, selecione um dia!", Toast.LENGTH_LONG).show();
            } else if (baseTime == 0) {
                Toast.makeText(this, "Por favor, defina uma duração!", Toast.LENGTH_LONG).show();
            } else {

                viewModel.inserirMateria(new Materia(nome, baseTime, dia));
                Toast.makeText(this, "Materia cadastrada!", Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }

    private void setupNumericUpDown() {
        timeFixOnFocusLoss(binding.etHour);
        timeFixOnFocusLoss(binding.etMinute);
        timeFixOnFocusLoss(binding.etSecond);

        binding.btnHourUp.setOnClickListener(v ->
                incrementValue(binding.etHour, 23));
        binding.btnMinuteUp.setOnClickListener(v ->
                incrementValue(binding.etMinute, 59));

        binding.btnSecondUp.setOnClickListener(v ->
                incrementValue(binding.etSecond, 59));

        binding.btnHourDown.setOnClickListener(v ->
                decrementValue(binding.etHour, 23));

        binding.btnMinuteDown.setOnClickListener(v ->
                decrementValue(binding.etMinute, 59));

        binding.btnSecondDown.setOnClickListener(v ->
                decrementValue(binding.etSecond, 59));
    }

    private void timeFixOnFocusLoss(TextInputEditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                int value = Integer.parseInt(editText.getText().toString());

                if (value > 59) {
                    editText.setText("59");
                }
            }
        });
    }

    private int getNumericValue(TextInputEditText editText) {
        String text = editText.getText() != null
                ? editText.getText().toString()
                : "00";
         return text.isEmpty() ? 0 : Integer.parseInt(text);

    }
    private void incrementValue(TextInputEditText editText, int maxValue) {
        int value = getNumericValue(editText);
        value++;

        if (value > maxValue) {
            value = 0;
        }
        editText.setText(String.format(Locale.getDefault(), "%02d", value));
    }

    private void decrementValue(TextInputEditText editText, int maxValue) {
        int value = getNumericValue(editText);
        value--;
        if (value < 0) {
            value = maxValue;
        }

        editText.setText(String.format(Locale.getDefault(), "%02d", value));
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