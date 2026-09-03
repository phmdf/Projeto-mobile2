package com.example.oficina1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Personalizar saudação
        SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String nome = pref.getString("user_name", "Cliente");
        TextView txtSaudacao = findViewById(R.id.txtSaudacao);
        txtSaudacao.setText(getString(R.string.greeting, nome));

        findViewById(R.id.btnVeiculos).setOnClickListener(v -> {
            startActivity(new Intent(this, VeiculosActivity.class));
        });

        findViewById(R.id.btnHistorico).setOnClickListener(v -> {
            startActivity(new Intent(this, HistoricoActivity.class));
        });

        findViewById(R.id.btnOrcamentos).setOnClickListener(v -> {
            startActivity(new Intent(this, OrcamentoActivity.class));
        });

        findViewById(R.id.btnPerfil).setOnClickListener(v -> {
            startActivity(new Intent(this, PerfilActivity.class));
        });

        findViewById(R.id.btnVerAtendimento).setOnClickListener(v -> {
            startActivity(new Intent(this, AtendimentoActivity.class));
        });
    }
}