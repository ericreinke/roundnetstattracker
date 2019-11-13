package com.example.roundnetstattracker.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roundnetstattracker.model.Game;

import java.util.List;

@Dao
public interface GameDAO {

    @Query("SELECT * FROM games")
    List<Game> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Game... game);

    @Delete
    void delete(Game game);

    @Query("DELETE FROM games")
    void deleteAll();
}