package com.example.chronofocus.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chronofocus.model.Materia;

import java.util.List;

public class ListViewUtils {
    public static void myArrayAdapter(List<Materia> list, ListView listView, Context context){
        ArrayAdapter<Materia> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
