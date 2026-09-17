package com.example.oficina1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Orcamento;
import com.example.oficina1.database.Veiculo;
import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private TextView txtVeiculo, txtPlaca;
    private RecyclerView recyclerHistorico;
    private OrcamentoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        txtVeiculo = findViewById(R.id.txtHistoricoVeiculo);
        txtPlaca = findViewById(R.id.txtPlacaHistorico);
        recyclerHistorico = findViewById(R.id.recyclerHistorico);

        recyclerHistorico.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrcamentoAdapter(new ArrayList<>());
        recyclerHistorico.setAdapter(adapter);

        findViewById(R.id.toolbarHistorico).setOnClickListener(v -> finish());

        carregarDados();
    }

    private void carregarDados() {
        SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int userId = pref.getInt("user_id", -1);

        if (userId != -1) {
            // Pegar o primeiro veículo do usuário para o histórico
            List<Veiculo> veiculos = AppDatabase.getInstance(this).veiculoDao().getByCliente(userId);
            if (!veiculos.isEmpty()) {
                Veiculo v = veiculos.get(0);
                txtVeiculo.setText(v.modelo + " " + v.ano);
                txtPlaca.setText(v.placa);

                // Carregar orçamentos/serviços deste veículo
                List<Orcamento> orcamentos = AppDatabase.getInstance(this).orcamentoDao().getByVeiculo(v.id);
                adapter.setOrcamentos(orcamentos);
            } else {
                txtVeiculo.setText("Nenhum veículo cadastrado");
                txtPlaca.setText("-");
            }
        }
    }
}
