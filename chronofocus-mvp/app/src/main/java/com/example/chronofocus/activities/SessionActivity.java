package com.example.chronofocus.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(SessionViewModel.initializer)).get(SessionViewModel.class);
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnStart = binding.btnStart;
        Button btnFinish = binding.btnFinish;
        Button btnPause = binding.btnPause;
        Button btnRestart = binding.btnRestart;
        Button btnResume = binding.btnResume;
        Button btnNextMateria = binding.btnNextMateria;
        ImageButton btnBack = binding.btnBack;


        /* DISCLAIMER (SÉRIO, MAS IGNORE SE JA ESTÁ CIENTE): Se o usuário clicar multiplas vezes no mesmo botão,
            diversas instnacias de countDownTimer serão criadas.
            O cerne do problema é que Garbage Collector não executará um free() na
            instancia até que o countdown chegue a zero, causando
         um memory leak e sérios travamentos na MainThread(UI Thread) em situações de multiplos cliques.*/

        // TEMPORARIO dessa forma não tem persistencia, vou implementar SessionTimer na view

        ArrayList<DaysWeek> days = new ArrayList<>();
        days.add(DaysWeek.FRIDAY);
        Materia proximaMateria = new Materia("Português", 432131, days, 2);


        binding.tvTitle.setText(R.string.sessao);

        long time = proximaMateria.getBaseTime();

        binding.tvMateria.setText(proximaMateria.getNome());
        binding.tvTimer.setText(TimerUtils.millisToFormattedTimeString(time));

        btnStart.setOnClickListener(l -> {
            btnStart.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.VISIBLE);

           new CountDownTimer(time, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    binding.tvTimer.setText(TimerUtils.millisToFormattedTimeString(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    Toast.makeText(context, "Sessão terminada!", Toast.LENGTH_SHORT).show();
                    btnStart.setVisibility(View.VISIBLE);
                    btnFinish.setVisibility(View.GONE);
                    btnPause.setVisibility(View.GONE);
                }

            }.start();
        });

    }
}