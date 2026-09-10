package com.example.oficina1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Orcamento;
import com.example.oficina1.database.Veiculo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CadastroOrcamentoActivity extends AppCompatActivity {

    private EditText edtPlaca, edtValor, edtDetalhes;
    private AutoCompleteTextView autoCompServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_orcamento);

        edtPlaca = findViewById(R.id.edtOrcamentoPlaca);
        edtValor = findViewById(R.id.edtOrcamentoValor);
        edtDetalhes = findViewById(R.id.edtOrcamentoDetalhes);
        autoCompServico = findViewById(R.id.autoCompleteServico);

        String[] servicos = {"Troca de Óleo", "Revisão", "Motor", "Câmbio", "Suspensão", "Freios", "Outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, servicos);
        autoCompServico.setAdapter(adapter);

        if (getIntent().hasExtra("placa")) {
            String placa = getIntent().getStringExtra("placa");
            edtPlaca.setText(placa);
            edtPlaca.setEnabled(false);
        }

        findViewById(R.id.btnSalvarOrcamento).setOnClickListener(v -> salvar());
    }

    private void salvar() {
        String placa = edtPlaca.getText().toString().trim().toUpperCase();
        String sValor = edtValor.getText().toString();
        String servicoSelecionado = autoCompServico.getText().toString();
        String detalhesAdicionais = edtDetalhes.getText().toString();

        if (placa.isEmpty() || sValor.isEmpty() || servicoSelecionado.isEmpty()) {
            Toast.makeText(this, "Preencha Placa, Serviço e Valor", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buscar veículo pela placa
        Veiculo veiculo = AppDatabase.getInstance(this).veiculoDao().getByPlaca(placa);
        if (veiculo == null) {
            Toast.makeText(this, "Veículo não encontrado com esta placa!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar se já existe orçamento para este veículo (opcional, conforme pedido)
        List<Orcamento> existentes = AppDatabase.getInstance(this).orcamentoDao().getByVeiculo(veiculo.id);
        if (!existentes.isEmpty()) {
            Toast.makeText(this, "Já existe um orçamento cadastrado para este veículo!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double valor = Double.parseDouble(sValor);
            String data = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            String detalhesCompletos = "Serviço: " + servicoSelecionado;
            if (!detalhesAdicionais.isEmpty()) {
                detalhesCompletos += "\nNotas: " + detalhesAdicionais;
            }

            Orcamento orcamento = new Orcamento(veiculo.id, valor, "Pendente", detalhesCompletos, data);
            AppDatabase.getInstance(this).orcamentoDao().insert(orcamento);

            Toast.makeText(this, "Orçamento criado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
        }
    }
}
