package com.example.googlemap.data.local;

import androidx.room.Insert;
import androidx.room.Query;

import com.example.googlemap.data.models.Figure;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void putFigure(List<Figure> figure);

    @Query("SELECT * FROM Figure")
    List<Figure> getFigure();


}
