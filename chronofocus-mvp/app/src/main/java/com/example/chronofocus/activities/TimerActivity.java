package com.example.chronofocus.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivityTimerBinding;

import java.sql.Time;

public class TimerActivity extends AppCompatActivity {
    private ActivityTimerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTimerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

             /* DISCLAIMER (SÉRIO, MAS IGNORE SE JA ESTÁ CIENTE): Se o usuário clicar multiplas vezes no mesmo botão,
            diversas instnacias de countDownTimer serão criadas.
            O cerne do problema é que Garbage Collector não executará um free() na
            instancia até que o countdown chegue a zero, causando
         um memory leak e sérios travamentos na MainThread(UI Thread) em situações de multiplos cliques.*/
        binding.button.setOnClickListener(l -> {
           new CountDownTimer(1000, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Time time = new Time(millisUntilFinished);
                    binding.textViewTimer.setText(String.format("%s", time.toString()));
                }

                @Override
                public void onFinish(){
                    finish();
                }
            }.start();

        });

    }
}