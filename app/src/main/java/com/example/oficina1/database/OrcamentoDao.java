package com.example.oficina1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface OrcamentoDao {
    @Query("SELECT * FROM orcamentos")
    List<Orcamento> getAll();

    @Query("SELECT * FROM orcamentos WHERE veiculoId = :veiculoId")
    List<Orcamento> getByVeiculo(int veiculoId);

    @Insert
    void insert(Orcamento orcamento);

    @Update
    void update(Orcamento orcamento);

    @Query("UPDATE orcamentos SET status = :status WHERE id = :id")
    void updateStatus(int id, String status);

    @Query("UPDATE orcamentos SET status = :status WHERE veiculoId = :veiculoId")
    void updateStatusByVeiculo(int veiculoId, String status);

    @Delete
    void delete(Orcamento orcamento);
}