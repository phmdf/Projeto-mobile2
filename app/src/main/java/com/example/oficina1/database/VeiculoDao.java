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

    @Insert
    void insert(Veiculo veiculo);

    @Delete
    void delete(Veiculo veiculo);
}