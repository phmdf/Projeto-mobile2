package com.example.oficina1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Veiculo;

public class CadastroVeiculoActivity extends AppCompatActivity {

    private EditText edtModelo, edtPlaca, edtAno, edtCor;
    private int veiculoId = -1;
    private String statusAtual = "Aguardando";
    private int clienteIdAtual = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);

        edtModelo = findViewById(R.id.edtModelo);
        edtPlaca = findViewById(R.id.edtPlaca);
        edtAno = findViewById(R.id.edtAno);
        edtCor = findViewById(R.id.edtCor);

        if (getIntent().hasExtra("veiculo_id")) {
            veiculoId = getIntent().getIntExtra("veiculo_id", -1);
            edtModelo.setText(getIntent().getStringExtra("modelo"));
            edtPlaca.setText(getIntent().getStringExtra("placa"));
            edtAno.setText(getIntent().getStringExtra("ano"));
            edtCor.setText(getIntent().getStringExtra("cor"));
            statusAtual = getIntent().getStringExtra("status");
            clienteIdAtual = getIntent().getIntExtra("cliente_id", -1);
            
            com.google.android.material.appbar.MaterialToolbar toolbar = findViewById(R.id.toolbarCadastro);
            toolbar.setTitle("Editar Veículo");
        }

        findViewById(R.id.btnSalvarVeiculo).setOnClickListener(v -> salvar());
        
        findViewById(R.id.toolbarCadastro).setOnClickListener(v -> finish());
    }

    private void salvar() {
        String modelo = edtModelo.getText().toString();
        String placa = edtPlaca.getText().toString().trim().toUpperCase();
        String ano = edtAno.getText().toString();
        String cor = edtCor.getText().toString();

        if (modelo.isEmpty() || placa.isEmpty()) {
            Toast.makeText(this, "Preencha ao menos Modelo e Placa", Toast.LENGTH_SHORT).show();
            return;
        }

        if (veiculoId == -1) {
            // Novo veículo - verificar placa única
            Veiculo existente = AppDatabase.getInstance(this).veiculoDao().getByPlaca(placa);
            if (existente != null) {
                Toast.makeText(this, "Já existe um veículo com esta placa!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
            int userId = pref.getInt("user_id", -1);
            Veiculo veiculo = new Veiculo(modelo, placa, ano, cor, "Aguardando", userId);
            AppDatabase.getInstance(this).veiculoDao().insert(veiculo);
            Toast.makeText(this, "Veículo salvo com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            // Editar existente - verificar se placa mudou e se a nova já existe
            Veiculo existente = AppDatabase.getInstance(this).veiculoDao().getByPlaca(placa);
            if (existente != null && existente.id != veiculoId) {
                Toast.makeText(this, "Já existe um outro veículo com esta placa!", Toast.LENGTH_SHORT).show();
                return;
            }

            Veiculo veiculo = new Veiculo(modelo, placa, ano, cor, statusAtual, clienteIdAtual);
            veiculo.id = veiculoId;
            AppDatabase.getInstance(this).veiculoDao().update(veiculo);
            Toast.makeText(this, "Veículo atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
