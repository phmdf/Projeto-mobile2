package com.example.oficina1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Veiculo;
import java.util.List;

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

    @Override
    protected void onResume() {
        super.onResume();
        verificarVeiculos();
    }

    private void verificarVeiculos() {
        SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int userId = pref.getInt("user_id", -1);
        String role = pref.getString("user_role", "");

        if ("CLIENTE".equals(role) && userId != -1) {
            List<Veiculo> veiculos = AppDatabase.getInstance(this).veiculoDao().getByCliente(userId);
            if (veiculos.isEmpty()) {
                mostrarDialogoPrimeiroVeiculo();
            }
        }
    }

    private void mostrarDialogoPrimeiroVeiculo() {
        new AlertDialog.Builder(this)
                .setTitle("Bem-vindo!")
                .setMessage("Identificamos que você ainda não possui veículos cadastrados. Para agendar serviços, é necessário cadastrar seu primeiro veículo.")
                .setPositiveButton("Cadastrar Agora", (dialog, which) -> {
                    startActivity(new Intent(this, CadastroVeiculoActivity.class));
                })
                .setCancelable(false)
                .show();
    }
}
