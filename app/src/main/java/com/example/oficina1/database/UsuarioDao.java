package com.example.oficina1.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuarios")
    List<Usuario> getAll();

    @Insert
    void insert(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE email = :email COLLATE NOCASE AND senha = :senha LIMIT 1")
    Usuario login(String email, String senha);

    @Query("SELECT * FROM usuarios WHERE email = :email COLLATE NOCASE LIMIT 1")
    Usuario getByEmail(String email);

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    Usuario getById(int id);

    @Query("UPDATE usuarios SET cargo = :role WHERE id = :id")
    void updateRole(int id, String role);

    @androidx.room.Update
    void update(Usuario usuario);
}
