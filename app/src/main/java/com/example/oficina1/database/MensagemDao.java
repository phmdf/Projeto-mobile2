package com.example.oficina1.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MensagemDao {
    @Query("SELECT * FROM mensagens WHERE remetenteId = :u1 AND destinatarioId = :u2 OR remetenteId = :u2 AND destinatarioId = :u1 ORDER BY data ASC")
    List<Mensagem> getConversa(int u1, int u2);

    @Insert
    void insert(Mensagem mensagem);
}