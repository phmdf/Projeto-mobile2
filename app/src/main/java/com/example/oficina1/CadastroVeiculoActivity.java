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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);

        edtModelo = findViewById(R.id.edtModelo);
        edtPlaca = findViewById(R.id.edtPlaca);
        edtAno = findViewById(R.id.edtAno);
        edtCor = findViewById(R.id.edtCor);

        findViewById(R.id.btnSalvarVeiculo).setOnClickListener(v -> salvar());
        
        findViewById(R.id.toolbarCadastro).setOnClickListener(v -> finish());
    }

    private void salvar() {
        String modelo = edtModelo.getText().toString();
        String placa = edtPlaca.getText().toString();
        String ano = edtAno.getText().toString();
        String cor = edtCor.getText().toString();

        if (modelo.isEmpty() || placa.isEmpty()) {
            Toast.makeText(this, "Preencha ao menos Modelo e Placa", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int userId = pref.getInt("user_id", -1);

        Veiculo veiculo = new Veiculo(modelo, placa, ano, cor, "Aguardando", userId);
        AppDatabase.getInstance(this).veiculoDao().insert(veiculo);

        Toast.makeText(this, "Veículo salvo com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
