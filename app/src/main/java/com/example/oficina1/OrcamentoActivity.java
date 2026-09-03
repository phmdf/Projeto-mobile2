package com.example.oficina1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class OrcamentoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orcamento);

        findViewById(R.id.btnAprovar).setOnClickListener(v -> {
            startActivity(new Intent(this, AprovacaoOrcamentoActivity.class));
        });
    }
}