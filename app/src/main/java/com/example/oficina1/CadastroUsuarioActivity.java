package com.example.oficina1;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha, edtConfirmaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtNome = findViewById(R.id.edtNomeCadastro);
        edtEmail = findViewById(R.id.edtEmailCadastro);
        edtSenha = findViewById(R.id.edtSenhaCadastro);
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenha);

        findViewById(R.id.btnCriarConta).setOnClickListener(v -> cadastrar());
        findViewById(R.id.btnVoltarLogin).setOnClickListener(v -> finish());
    }

    private void cadastrar() {
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString();
        String confirma = edtConfirmaSenha.getText().toString();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirma)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar se e-mail já existe
        Usuario existende = AppDatabase.getInstance(this).usuarioDao().getByEmail(email);
        if (existende != null) {
            Toast.makeText(this, "E-mail já cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario novoUsuario = new Usuario(nome, email, senha);
        AppDatabase.getInstance(this).usuarioDao().insert(novoUsuario);

        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}