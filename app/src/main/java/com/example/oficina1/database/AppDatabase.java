package com.example.oficina1.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Veiculo.class, Usuario.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract VeiculoDao veiculoDao();
    public abstract UsuarioDao usuarioDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "oficina_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Simplificação para este exemplo
                    .build();
        }
        return instance;
    }
}