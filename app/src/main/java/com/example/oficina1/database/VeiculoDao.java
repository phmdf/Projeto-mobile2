package com.example.oficina1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface VeiculoDao {
    @Query("SELECT * FROM veiculos")
    List<Veiculo> getAll();

    @Query("SELECT * FROM veiculos WHERE clienteId = :clienteId")
    List<Veiculo> getByCliente(int clienteId);

    @Insert
    void insert(Veiculo veiculo);

    @Query("UPDATE veiculos SET status = :status WHERE id = :id")
    void updateStatus(int id, String status);

    @Delete
    void delete(Veiculo veiculo);
}
