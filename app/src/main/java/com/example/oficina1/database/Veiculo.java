package com.example.oficina1.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "veiculos", indices = {@Index(value = {"placa"}, unique = true)})
public class Veiculo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String modelo;
    public String placa;
    public String ano;
    public String cor;
    public String status; // "Aguardando", "Em Manutenção", "Pronto", "Entregue"
    public int clienteId;

    public Veiculo(String modelo, String placa, String ano, String cor, String status, int clienteId) {
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
        this.cor = cor;
        this.status = status;
        this.clienteId = clienteId;
    }
}