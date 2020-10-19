package com.example.googlemap;

import android.app.Application;

import androidx.room.Room;

import com.example.googlemap.data.local.Database;

public class App extends Application {

    public static Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, Database.class, "Database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}
