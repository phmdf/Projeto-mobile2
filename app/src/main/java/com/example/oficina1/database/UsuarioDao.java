package com.example.oficina1.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsuarioDao {
    @Insert
    void insert(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario login(String email, String senha);

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario getByEmail(String email);
}