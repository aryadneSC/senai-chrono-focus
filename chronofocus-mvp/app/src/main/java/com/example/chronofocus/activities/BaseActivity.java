package com.example.chronofocus.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chronofocus.R;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int currentTheme = prefs.getInt("current_theme", 0);

        if (currentTheme == 1) {
            setTheme(R.style.Base_Theme_Chronofocusmvp_Colorido);
        } else {
            setTheme(R.style.Base_Theme_Chronofocusmvp);
        }
        super.onCreate(savedInstanceState);
    }
}