package com.example.chronofocus.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chronofocus.R;
import com.example.chronofocus.model.DaysWeek;
import com.example.chronofocus.model.Materia;

import java.util.List;

public class MateriaAdapter extends ArrayAdapter<Materia> {

    public MateriaAdapter(Context context, List<Materia> materias) {
        super(context, 0, materias);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_materia, parent, false);
        }

        Materia materia = getItem(position);

        TextView tvNome = convertView.findViewById(R.id.tvNomeMateria);
        TextView tvSub = convertView.findViewById(R.id.tvSubtextMateria);

        StringBuilder dias = new StringBuilder();

        for (DaysWeek day : materia.getDay()) {
            if (dias.length() > 0) {
                dias.append(", ");
            }
            dias.append(day.toString());
        }

        tvNome.setText(materia.getNome());
        tvSub.setText(
                String.format(
                        "%s - %s",
                        dias,
                        TimerUtils.millisToFormattedTimeString(materia.getBaseTime())
                )
        );
        return convertView;
    }
}
