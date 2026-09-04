package com.example.oficina1;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Usuario;
import com.example.oficina1.database.UsuarioDao;
import java.util.List;

public class AdminUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuarios);

        recyclerView = findViewById(R.id.recyclerAdminUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        new Thread(() -> {
            List<Usuario> lista = AppDatabase.getInstance(this).usuarioDao().getAll();
            runOnUiThread(() -> {
                recyclerView.setAdapter(new UsuarioAdapter(lista, this::mostrarOpcoesRole));
            });
        }).start();
    }

    private void mostrarOpcoesRole(Usuario usuario) {
        String[] roles = {"CLIENTE", "GERENTE"};
        new AlertDialog.Builder(this)
            .setTitle("Mudar Cargo: " + usuario.nome)
            .setItems(roles, (dialog, which) -> {
                String novoCargo = roles[which];
                new Thread(() -> {
                    // Note: UsuarioDao needs an update query for role
                    // For now, let's just use a query if it exists or add it
                    AppDatabase.getInstance(this).usuarioDao().updateRole(usuario.id, novoCargo);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Cargo atualizado!", Toast.LENGTH_SHORT).show();
                        carregarUsuarios();
                    });
                }).start();
            })
            .show();
    }
}