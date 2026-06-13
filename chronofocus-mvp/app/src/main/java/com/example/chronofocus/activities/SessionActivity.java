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
import com.example.chronofocus.utils.TimerUtils;
import com.example.chronofocus.viewmodels.SessionViewModel;

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

        /* DISCLAIMER (SÉRIO, MAS IGNORE SE JA ESTÁ CIENTE): Se o usuário clicar multiplas vezes no mesmo botão,
            diversas instnacias de countDownTimer serão criadas.
            O cerne do problema é que Garbage Collector não executará um free() na
            instancia até que o countdown chegue a zero, causando
         um memory leak e sérios travamentos na MainThread(UI Thread) em situações de multiplos cliques.*/

        viewModel.getNextMateria();

        //long time = proximaMateria.getBaseTime();
        long time = 10000;
        binding.button.setOnClickListener(l -> {
           new CountDownTimer(time, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // talvez adicionar titulo da materia em cima
                    binding.textViewTimer.setText(TimerUtils.millisToFormattedTimeString(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    // SessionViewModel.GetNextMateria(),
                    // se conseguir, prossegue, se não, finish().
                    // GetNextMateria() chama SessionManager
                    //
                    // Materia materia = SessionViewModel.GetNextMateria()
                    // time = materia.getBaseTime();
                }
            }.start();
        });
    }
}