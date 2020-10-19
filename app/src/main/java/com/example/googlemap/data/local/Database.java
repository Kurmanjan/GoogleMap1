package com.example.googlemap.data.local;

import androidx.room.RoomDatabase;

import com.example.googlemap.data.models.Figure;

@androidx.room.Database(entities ={ Figure.class},version = 1)
public abstract class Database extends RoomDatabase {
    public abstract Dao daoDs();
}
