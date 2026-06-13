package com.example.chronofocus.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivityTimerBinding;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.model.Status;
import com.example.chronofocus.utils.TimerUtil;
import com.example.chronofocus.viewmodels.SessionViewModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SessionActivity extends AppCompatActivity {
    private ActivityTimerBinding binding;
    private SessionViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTimerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(SessionViewModel.initializer)).get(SessionViewModel.class);
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // TESTES (NÃO É FINAL)



        Materia proximaMateria = new Materia("Russo",
                TimerUtil.convertToMillis(10),
                DaysWeek.FRIDAY, 3);


        binding.button.setOnClickListener(l -> {
           new CountDownTimer(proximaMateria.getBaseTime(), 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    Time time = new Time(millisUntilFinished);
                    binding.textViewTimer.setText(String.format("%s", time.toString()));
                }

                @Override
                public void onFinish() {
                    // SessionViewModel.GetNextMateria(),
                    // se conseguir, prossegue, se não, finish().
                    // GetNextMateria() chama SessionManager
                }
            }.start();




        });

    }
}