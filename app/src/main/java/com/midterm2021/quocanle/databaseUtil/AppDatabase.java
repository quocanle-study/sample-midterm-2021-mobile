package com.midterm2021.quocanle.databaseUtil;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.midterm2021.quocanle.DAO.ActionDAO;
import com.midterm2021.quocanle.model.ActionExcute;

@Database(entities = {ActionExcute.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract ActionDAO actionDAO();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "database-actionexcute").build();
        }
        return instance;
    }
}
