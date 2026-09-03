package com.example.oficina1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AprovacaoOrcamentoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovacao_orcamento);

        findViewById(R.id.btnConfirmarAprovacao).setOnClickListener(v -> finish());
        findViewById(R.id.btnCancelarAprovacao).setOnClickListener(v -> finish());
    }
}