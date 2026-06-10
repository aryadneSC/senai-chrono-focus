package com.example.chronofocus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText edtNovaMateria;
    private Button btnAdicionar;
    private ListView lvMaterias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtNovaMateria = findViewById(R.id.edtNovaMateria);
        btnAdicionar = findViewById(R.id.btnAdicionarMateria);
        lvMaterias = findViewById(R.id.listVMaterias);

        Button btnVoltar = findViewById(R.id.btnVoltarMateria);
        btnVoltar.setOnClickListener(v -> finish());

        btnAdicionar.setOnClickListener(v -> {
            String nomeMateria = edtNovaMateria.getText().toString().trim();

            if (nomeMateria.isEmpty()) {
                edtNovaMateria.setError("Insira o nome da matéria!");
                edtNovaMateria.requestFocus();
            } else {
                Toast.makeText(MainActivity.this, "Matéria adicionada!", Toast.LENGTH_SHORT).show();
                edtNovaMateria.setText("");
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}