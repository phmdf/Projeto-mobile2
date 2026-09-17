package com.example.oficina1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private TextView txtNome, txtEmail;
    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtNome = findViewById(R.id.txtNomeUsuario);
        txtEmail = findViewById(R.id.txtEmailUsuario);

        findViewById(R.id.btnEditarPerfil).setOnClickListener(v -> mostrarDialogoEdicao());

        findViewById(R.id.btnAlterarSenha).setOnClickListener(v -> mostrarDialogoAlterarSenha());

        findViewById(R.id.btnNotificacoes).setOnClickListener(v -> {
            startActivity(new Intent(this, NotificacoesActivity.class));
        });

        findViewById(R.id.btnSair).setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
            pref.edit().clear().apply();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        carregarDados();
    }

    private void carregarDados() {
        SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int userId = pref.getInt("user_id", -1);

        if (userId != -1) {
            usuarioLogado = AppDatabase.getInstance(this).usuarioDao().getById(userId);
            if (usuarioLogado != null) {
                txtNome.setText(usuarioLogado.nome);
                txtEmail.setText(usuarioLogado.email);
            }
        }
    }

    private void mostrarDialogoEdicao() {
        if (usuarioLogado == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Perfil");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final EditText inputNome = new EditText(this);
        inputNome.setHint("Nome");
        inputNome.setText(usuarioLogado.nome);
        layout.addView(inputNome);

        final EditText inputEmail = new EditText(this);
        inputEmail.setHint("E-mail");
        inputEmail.setText(usuarioLogado.email);
        layout.addView(inputEmail);

        builder.setView(layout);

        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String novoNome = inputNome.getText().toString().trim();
            String novoEmail = inputEmail.getText().toString().trim();

            if (!novoNome.isEmpty() && !novoEmail.isEmpty()) {
                usuarioLogado.nome = novoNome;
                usuarioLogado.email = novoEmail;

                AppDatabase.getInstance(this).usuarioDao().update(usuarioLogado);
                
                // Atualizar SharedPreferences também para manter a saudação correta no Dashboard
                SharedPreferences pref = getSharedPreferences("app_prefs", MODE_PRIVATE);
                pref.edit().putString("user_name", novoNome).apply();

                txtNome.setText(novoNome);
                txtEmail.setText(novoEmail);
                Toast.makeText(this, "Perfil atualizado!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarDialogoAlterarSenha() {
        if (usuarioLogado == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alterar Senha");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final EditText inputSenhaAtual = new EditText(this);
        inputSenhaAtual.setHint("Senha Atual");
        inputSenhaAtual.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
        layout.addView(inputSenhaAtual);

        final EditText inputNovaSenha = new EditText(this);
        inputNovaSenha.setHint("Nova Senha");
        inputNovaSenha.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
        layout.addView(inputNovaSenha);

        final EditText inputConfirmaSenha = new EditText(this);
        inputConfirmaSenha.setHint("Confirmar Nova Senha");
        inputConfirmaSenha.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
        layout.addView(inputConfirmaSenha);

        builder.setView(layout);

        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String senhaAtual = inputSenhaAtual.getText().toString();
            String novaSenha = inputNovaSenha.getText().toString();
            String confirmaSenha = inputConfirmaSenha.getText().toString();

            if (senhaAtual.isEmpty() || novaSenha.isEmpty() || confirmaSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senhaAtual.equals(usuarioLogado.senha)) {
                Toast.makeText(this, "Senha atual incorreta", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!novaSenha.equals(confirmaSenha)) {
                Toast.makeText(this, "As novas senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            usuarioLogado.senha = novaSenha;
            AppDatabase.getInstance(this).usuarioDao().update(usuarioLogado);
            Toast.makeText(this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
