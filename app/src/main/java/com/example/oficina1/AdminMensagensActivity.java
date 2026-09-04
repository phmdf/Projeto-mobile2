package com.example.oficina1;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oficina1.database.AppDatabase;
import com.example.oficina1.database.Mensagem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminMensagensActivity extends AppCompatActivity {

    private EditText edtTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mensagens);

        edtTexto = findViewById(R.id.edtMensagemTexto);

        findViewById(R.id.btnEnviarMensagem).setOnClickListener(v -> enviar());
        
        findViewById(R.id.btnAnexarFoto).setOnClickListener(v -> {
            Toast.makeText(this, "Anexar foto (Simulado)", Toast.LENGTH_SHORT).show();
        });
    }

    private void enviar() {
        String texto = edtTexto.getText().toString();
        if (texto.isEmpty()) return;

        int remetente = getSharedPreferences("app_prefs", MODE_PRIVATE).getInt("user_id", 0);
        int destinatario = 2; // Simulado para um cliente qualquer
        String data = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        Mensagem m = new Mensagem(remetente, destinatario, texto, null, data);
        new Thread(() -> {
            AppDatabase.getInstance(this).mensagemDao().insert(m);
            runOnUiThread(() -> {
                edtTexto.setText("");
                Toast.makeText(this, "Mensagem enviada!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}