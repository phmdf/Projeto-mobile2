package com.example.oficina1.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orcamentos")
public class Orcamento {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public int veiculoId;
    public double valorTotal;
    public String status; // "Pendente", "Aprovado", "Recusado"
    public String detalhes;
    public String dataCriacao;

    public Orcamento(int veiculoId, double valorTotal, String status, String detalhes, String dataCriacao) {
        this.veiculoId = veiculoId;
        this.valorTotal = valorTotal;
        this.status = status;
        this.detalhes = detalhes;
        this.dataCriacao = dataCriacao;
    }
}