package com.example.oficina1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        findViewById(R.id.btnEntrar).setOnClickListener(v -> login());
        
        findViewById(R.id.btnIrCadastro).setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroUsuarioActivity.class));
        });
    }

    private void login() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha e-mail e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = AppDatabase.getInstance(this).usuarioDao().login(email, senha);

        if (usuario != null) {
            // Salvar sessão
            SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
            pref.edit().putString("user_name", usuario.nome).apply();
            pref.edit().putInt("user_id", usuario.id).apply();
            pref.edit().putString("user_role", usuario.cargo).apply();

            if ("GERENTE".equals(usuario.cargo)) {
                startActivity(new Intent(this, AdminDashboardActivity.class));
            } else {
                startActivity(new Intent(this, DashboardActivity.class));
            }
            finish();
        } else {
            Toast.makeText(this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
        }
    }
}