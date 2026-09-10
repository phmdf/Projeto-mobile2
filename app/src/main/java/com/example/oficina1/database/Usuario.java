package com.example.oficina1.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios", indices = {@Index(value = {"email"}, unique = true)})
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String nome;
    public String email;
    public String senha;
    public String cargo;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String cargo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
    }
}
