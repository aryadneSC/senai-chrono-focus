package com.example.chronofocus;
import com.example.chronofocus.entities.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Time;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button btn = findViewById(R.id.button);
        TextView tx = findViewById(R.id.textView_Timer);


        /*
        materia.getBaseTime();

        public long getBaseTime() {
            return time.getBaseTime();
        }
        */

        // materia.Conclude();
        // ResetTimer();
        //



        btn.setOnClickListener(l -> {
            long millis = getCurrentMateriaBaseTime();
            CountDownTimer timer = new CountDownTimer(millis, 1000) {
                @Override

                public void onFinish() {

                }

                @Override
                public void onTick(long millisUntilFinished) {
                    Time time = new Time(millisUntilFinished);
                    tx.setText(String.format("%s", time));
                    /*
                    if() {
//                        this.cancel();
                    }
                    */

                }
            }.start();

        });
    }

    private long getCurrentMateriaBaseTime() {
        Intent intent = new Intent();

        if(intent.hasExtra("subject")) {
            Materia subject = (Materia)intent.getSerializableExtra("subject");

           return subject.getBaseTime();
        }

        return 0;
    }
}