package com.example.roundnetstattracker.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roundnetstattracker.model.TeamGameProfile;

import java.util.List;

@Dao
public interface TeamProfileDAO {
    @Query("SELECT * FROM team_profiles")
    List<TeamGameProfile> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TeamGameProfile... profiles);

    @Delete
    void delete(TeamGameProfile profiles);

    @Query("DELETE FROM team_profiles")
    void deleteAll();
}
