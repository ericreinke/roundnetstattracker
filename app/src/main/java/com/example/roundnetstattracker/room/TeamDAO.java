package com.example.roundnetstattracker.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roundnetstattracker.model.Team;

import java.util.List;

@Dao
public interface TeamDAO {

    @Query("SELECT * FROM teams")
    List<Team> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Team... team);

    @Delete
    void delete(Team team);

    @Query("DELETE FROM teams")
    void deleteAll();
}
