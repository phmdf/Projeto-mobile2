package com.example.oficina1.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mensagens")
public class Mensagem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public int remetenteId;
    public int destinatarioId;
    public String texto;
    public String fotoPath;
    public String data;

    public Mensagem(int remetenteId, int destinatarioId, String texto, String fotoPath, String data) {
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
        this.texto = texto;
        this.fotoPath = fotoPath;
        this.data = data;
    }
}