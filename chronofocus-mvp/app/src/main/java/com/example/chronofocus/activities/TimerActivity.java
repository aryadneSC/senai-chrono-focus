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

        binding.button.setOnClickListener(l -> {
            long millis = 1000;
            CountDownTimer timer = new CountDownTimer(millis, 1000) {
                @Override

                public void onFinish() {

                }

                @Override
                public void onTick(long millisUntilFinished) {
                    Time time = new Time(millisUntilFinished);
                    binding.textViewTimer.setText(String.format("%s", time));
                    /*
                    if() {
//                        this.cancel();
                    }
                    */

                }
            }.start();

        });

    }
}