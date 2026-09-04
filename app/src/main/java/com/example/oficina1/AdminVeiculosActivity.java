package com.example.oficina1;

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

        adapter = new VeiculoAdapter(new ArrayList<>(), this::mostrarOpcoesStatus);
        recyclerView.setAdapter(adapter);

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

    private void mostrarOpcoesStatus(Veiculo veiculo) {
        String[] opcoes = {"Aguardando", "Em Manutenção", "Pronto", "Entregue"};
        
        new AlertDialog.Builder(this)
            .setTitle("Mudar Status: " + veiculo.placa)
            .setItems(opcoes, (dialog, which) -> {
                String novoStatus = opcoes[which];
                AppDatabase.getInstance(this).usuarioDao(); // Dummy call to ensure DB init if needed
                AppDatabase.getInstance(this).veiculoDao().updateStatus(veiculo.id, novoStatus);
                Toast.makeText(this, "Status atualizado!", Toast.LENGTH_SHORT).show();
                carregarVeiculos();
            })
            .show();
    }
}