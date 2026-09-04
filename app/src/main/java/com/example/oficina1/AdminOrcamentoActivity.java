package com.example.oficina1;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Orcamento;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminOrcamentoActivity extends AppCompatActivity {

    private EditText edtVeiculoId, edtValor, edtDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orcamento);

        edtVeiculoId = findViewById(R.id.edtOrcamentoVeiculoId);
        edtValor = findViewById(R.id.edtOrcamentoValor);
        edtDetalhes = findViewById(R.id.edtOrcamentoDetalhes);

        findViewById(R.id.btnSalvarOrcamento).setOnClickListener(v -> salvar());
    }

    private void salvar() {
        String sVeiculoId = edtVeiculoId.getText().toString();
        String sValor = edtValor.getText().toString();
        String detalhes = edtDetalhes.getText().toString();

        if (sVeiculoId.isEmpty() || sValor.isEmpty()) {
            Toast.makeText(this, "Preencha Veículo e Valor", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int veiculoId = Integer.parseInt(sVeiculoId);
            double valor = Double.parseDouble(sValor);
            String data = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            Orcamento orcamento = new Orcamento(veiculoId, valor, "Pendente", detalhes, data);
            AppDatabase.getInstance(this).orcamentoDao().insert(orcamento);

            Toast.makeText(this, "Orçamento criado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
        }
    }
}