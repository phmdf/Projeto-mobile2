package com.example.oficina1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Orcamento;
import java.util.ArrayList;
import java.util.List;

public class AdminOrcamentoActivity extends AppCompatActivity {

    private OrcamentoAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orcamento);

        recyclerView = findViewById(R.id.recyclerAdminOrcamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrcamentoAdapter(new ArrayList<>(), this::onOrcamentoClicked);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnNovoOrcamentoToolbar).setOnClickListener(v -> 
            startActivity(new Intent(this, CadastroOrcamentoActivity.class))
        );

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbarAdminOrcamentos);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarOrcamentos();
    }

    private void carregarOrcamentos() {
        List<Orcamento> lista = AppDatabase.getInstance(this).orcamentoDao().getAll();
        adapter.setOrcamentos(lista);
    }

    private void onOrcamentoClicked(Orcamento orcamento) {
        String[] opcoes = {"Aguardando", "Em Manutenção", "Pronto", "Entregue", "Recusado"};

        new AlertDialog.Builder(this)
            .setTitle("Mudar Status do Orçamento")
            .setItems(opcoes, (dialog, which) -> {
                String novoStatus = opcoes[which];
                
                // Atualiza o orçamento
                AppDatabase.getInstance(this).orcamentoDao().updateStatus(orcamento.id, novoStatus);
                
                // Atualiza o veículo associado (sincronização)
                // Nota: "Recusado" não é um status padrão de veículo, mas podemos decidir o que fazer.
                // Se for um dos status de veículo, atualizamos.
                if (!novoStatus.equals("Recusado")) {
                    AppDatabase.getInstance(this).veiculoDao().updateStatus(orcamento.veiculoId, novoStatus);
                }
                
                Toast.makeText(this, "Status atualizado em ambos!", Toast.LENGTH_SHORT).show();
                carregarOrcamentos();
            })
            .show();
    }
}