package com.example.chronofocus.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivitySessionBinding;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;
import com.example.chronofocus.model.SessionTimer;
import com.example.chronofocus.utils.TimerUtils;
import com.example.chronofocus.viewmodels.SessionViewModel;

import java.util.ArrayList;

public class SessionActivity extends AppCompatActivity {
    private ActivitySessionBinding binding;
    private SessionViewModel viewModel;
    private AppCompatActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySessionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(SessionViewModel.initializer)).get(SessionViewModel.class);
        initialize();



        /* DISCLAIMER (SÉRIO, MAS IGNORE SE JA ESTÁ CIENTE): Se o usuário clicar multiplas vezes no mesmo botão,
            diversas instnacias de countDownTimer serão criadas.
            O cerne do problema é que Garbage Collector não executará um free() na
            instancia até que o countdown chegue a zero, causando
         um memory leak e sérios travamentos na MainThread(UI Thread) em situações de multiplos cliques.*/





    }
    private void initialize() {
        // usar observer
        binding.tvTitle.setText(R.string.sessao);
        binding.tvMateria.setText(viewModel.getNextMateriaName());
        //binding.tvTimer.setText(TimerUtils.millisToFormattedTimeString(viewModel.getTime()));

        binding.btnStart.setOnClickListener(v -> onStartTimer());
        binding.btnFinish.setOnClickListener(v -> onFinishSession());
        binding.btnPause.setOnClickListener(v -> onPauseTimer());
        binding.btnRestart.setOnClickListener(v -> onRestartTimer());
        binding.btnResume.setOnClickListener(v -> onResumeTimer());
        binding.btnNextMateria.setOnClickListener(v -> onNextMateria());
        binding.btnBack.setOnClickListener(v -> onBackPressedCustom());
    }
    private void onStartTimer() {
        binding.btnStart.setVisibility(View.GONE);
        binding.btnFinish.setVisibility(View.VISIBLE);
        binding.btnPause.setVisibility(View.VISIBLE);

        new CountDownTimer(300000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvTimer.setText(TimerUtils.millisToFormattedTimeString(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Toast.makeText(context, "Sessão terminada!", Toast.LENGTH_SHORT).show();
                binding.btnStart.setVisibility(View.VISIBLE);
                binding.btnFinish.setVisibility(View.GONE);
                binding.btnPause.setVisibility(View.GONE);
            }

        }.start();
    }
    private void onPauseTimer() {}
    private void onResumeTimer() {}
    private void onFinishSession() {}
    private void onRestartTimer() {}
    private void onNextMateria() {}
    private void onBackPressedCustom() {}
}