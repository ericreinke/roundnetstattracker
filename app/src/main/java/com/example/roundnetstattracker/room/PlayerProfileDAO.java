package com.example.roundnetstattracker.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.roundnetstattracker.model.PlayerGameProfile;

import java.util.List;

@Dao
public interface PlayerProfileDAO {

    @Query("SELECT * FROM player_profiles")
    List<PlayerGameProfile> getAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PlayerGameProfile... profiles);

    @Delete
    void delete(PlayerGameProfile profiles);

    @Query("DELETE FROM player_profiles")
    void deleteAll();
}
