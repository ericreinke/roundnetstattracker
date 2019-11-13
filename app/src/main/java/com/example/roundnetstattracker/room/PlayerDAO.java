package com.example.roundnetstattracker.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roundnetstattracker.model.Player;

import java.util.List;

@Dao
public interface PlayerDAO {

    @Query("SELECT * FROM players")
    List<Player> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Player... player);

    @Delete
    void delete(Player player);

    @Query("DELETE FROM players")
    void deleteAll();
}
