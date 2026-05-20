package br.com.jeniffer.controlechavespix;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseProvider {
    private static AppDatabase db;

    public static AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "controle_pix_db"
            ).allowMainThreadQueries().build();
        }
        return db;
    }
}