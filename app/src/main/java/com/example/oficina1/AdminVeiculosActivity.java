package com.example.oficina1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Veiculo;
import java.util.ArrayList;
import java.util.List;

public class AdminVeiculosActivity extends AppCompatActivity {

    private VeiculoAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_veiculos);

        recyclerView = findViewById(R.id.recyclerAdminVeiculos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VeiculoAdapter(new ArrayList<>(), this::mostrarOpcoesVeiculo);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fabAddVeiculo).setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroVeiculoActivity.class));
        });

        findViewById(R.id.toolbarAdminVeiculos).setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarVeiculos();
    }

    private void carregarVeiculos() {
        List<Veiculo> lista = AppDatabase.getInstance(this).veiculoDao().getAll();
        adapter.setVeiculos(lista);
    }

    private void mostrarOpcoesVeiculo(Veiculo veiculo) {
        String[] opcoes = {"Criar Orçamento", "Mudar Status", "Editar", "Excluir"};

        new AlertDialog.Builder(this)
            .setTitle("Veículo: " + veiculo.placa)
            .setItems(opcoes, (dialog, which) -> {
                switch (which) {
                    case 0: criarOrcamento(veiculo); break;
                    case 1: mostrarOpcoesStatus(veiculo); break;
                    case 2: editarVeiculo(veiculo); break;
                    case 3: confirmarExclusao(veiculo); break;
                }
            })
            .show();
    }

    private void criarOrcamento(Veiculo veiculo) {
        Intent intent = new Intent(this, CadastroOrcamentoActivity.class);
        intent.putExtra("placa", veiculo.placa);
        startActivity(intent);
    }

    private void editarVeiculo(Veiculo veiculo) {
        Intent intent = new Intent(this, CadastroVeiculoActivity.class);
        intent.putExtra("veiculo_id", veiculo.id);
        intent.putExtra("modelo", veiculo.modelo);
        intent.putExtra("placa", veiculo.placa);
        intent.putExtra("ano", veiculo.ano);
        intent.putExtra("cor", veiculo.cor);
        intent.putExtra("status", veiculo.status);
        intent.putExtra("cliente_id", veiculo.clienteId);
        startActivity(intent);
    }

    private void confirmarExclusao(Veiculo veiculo) {
        new AlertDialog.Builder(this)
            .setTitle("Excluir Veículo")
            .setMessage("Tem certeza que deseja excluir o veículo " + veiculo.modelo + " (" + veiculo.placa + ")?")
            .setPositiveButton("Sim", (dialog, which) -> {
                AppDatabase.getInstance(this).veiculoDao().delete(veiculo);
                Toast.makeText(this, "Veículo excluído!", Toast.LENGTH_SHORT).show();
                carregarVeiculos();
            })
            .setNegativeButton("Não", null)
            .show();
    }

    private void mostrarOpcoesStatus(Veiculo veiculo) {
        String[] opcoesStatus = {"Aguardando", "Em Manutenção", "Pronto", "Entregue"};
        
        new AlertDialog.Builder(this)
            .setTitle("Mudar Status: " + veiculo.placa)
            .setItems(opcoesStatus, (dialog, which) -> {
                String novoStatus = opcoesStatus[which];
                
                // Atualiza o veículo
                AppDatabase.getInstance(this).veiculoDao().updateStatus(veiculo.id, novoStatus);
                
                // Sincroniza com o orçamento (atualiza todos os orçamentos deste veículo para o mesmo status)
                AppDatabase.getInstance(this).orcamentoDao().updateStatusByVeiculo(veiculo.id, novoStatus);
                
                Toast.makeText(this, "Status atualizado!", Toast.LENGTH_SHORT).show();
                carregarVeiculos();
            })
            .show();
    }
}