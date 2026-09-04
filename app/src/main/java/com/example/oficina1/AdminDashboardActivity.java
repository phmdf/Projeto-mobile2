package com.example.oficina1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String nome = pref.getString("user_name", "Gerente");
        TextView txtSaudacao = findViewById(R.id.txtSaudacaoAdmin);
        txtSaudacao.setText("Olá, " + nome + "!");

        findViewById(R.id.cardGestaoVeiculos).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminVeiculosActivity.class));
        });

        findViewById(R.id.cardNovoOrcamento).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminOrcamentoActivity.class));
        });

        findViewById(R.id.cardGestaoUsuarios).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminUsuariosActivity.class));
        });

        findViewById(R.id.cardMensagens).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminMensagensActivity.class));
        });

        findViewById(R.id.btnSairAdmin).setOnClickListener(v -> {
            pref.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}