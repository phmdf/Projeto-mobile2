package com.example.oficina1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Veiculo;
import java.util.ArrayList;
import java.util.List;

public class VeiculosActivity extends AppCompatActivity {

    private VeiculoAdapter adapter;
    private RecyclerView recyclerVeiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculos);

        recyclerVeiculos = findViewById(R.id.recyclerVeiculos);
        recyclerVeiculos.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new VeiculoAdapter(new ArrayList<>());
        recyclerVeiculos.setAdapter(adapter);

        findViewById(R.id.fabNovoVeiculo).setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroVeiculoActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarVeiculos();
    }

    private void carregarVeiculos() {
        SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int userId = pref.getInt("user_id", -1);
        
        List<Veiculo> lista;
        if (userId != -1) {
            lista = AppDatabase.getInstance(this).veiculoDao().getByCliente(userId);
        } else {
            lista = new ArrayList<>();
        }
        adapter.setVeiculos(lista);
    }
}