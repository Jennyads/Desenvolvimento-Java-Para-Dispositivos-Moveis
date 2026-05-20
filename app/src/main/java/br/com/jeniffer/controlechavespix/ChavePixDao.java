package br.com.jeniffer.controlechavespix;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChavePixDao {

    @Insert
    void insert(ChavePix chavePix);

    @Update
    void update(ChavePix chavePix);

    @Delete
    void delete(ChavePix chavePix);

    @Query("SELECT * FROM chaves_pix")
    List<ChavePix> getAll();
}
