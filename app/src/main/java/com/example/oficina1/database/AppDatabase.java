package com.example.oficina1.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Veiculo.class, Usuario.class, Orcamento.class, Mensagem.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract VeiculoDao veiculoDao();
    public abstract UsuarioDao usuarioDao();
    public abstract OrcamentoDao orcamentoDao();
    public abstract MensagemDao mensagemDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "oficina_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            // Criar gerente padrão se não existir
            Usuario admin = instance.usuarioDao().getByEmail("admin@oficina.com");
            if (admin == null) {
                instance.usuarioDao().insert(new Usuario("Gerente Geral", "admin@oficina.com", "admin123", "GERENTE"));
            }
        }
        return instance;
    }
}
