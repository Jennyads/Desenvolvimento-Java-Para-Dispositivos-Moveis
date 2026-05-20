package br.com.jeniffer.controlechavespix;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChavePix.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ChavePixDao chavePixDao();
}