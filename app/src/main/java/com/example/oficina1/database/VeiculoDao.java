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

    @Query("SELECT * FROM veiculos WHERE placa = :placa LIMIT 1")
    Veiculo getByPlaca(String placa);

    @Insert
    void insert(Veiculo veiculo);

    @androidx.room.Update
    void update(Veiculo veiculo);

    @Query("UPDATE veiculos SET status = :status WHERE id = :id")
    void updateStatus(int id, String status);

    @Delete
    void delete(Veiculo veiculo);
}
