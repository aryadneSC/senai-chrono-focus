package com.example.chronofocus.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.chronofocus.R;

public class ThemeUtils {

    private static final String PREFS_NAME = "chrono_prefs";
    private static final String KEY_TEMA = "tema_colorido";

    public static boolean isColorido(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_TEMA, false);
    }

    public static void setColorido(Context context, boolean colorido) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_TEMA, colorido)
                .apply();
    }

    public static void aplicarTema(Context context) {
        if (isColorido(context)) {
            context.setTheme(R.style.Base_Theme_Chronofocusmvp_Colorido);
        }
    }
}