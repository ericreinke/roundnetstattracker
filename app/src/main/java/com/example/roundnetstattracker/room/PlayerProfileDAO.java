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

    @Query("SELECT * FROM player_profiles WHERE uid = :uid")
    PlayerGameProfile getPlayerProfile(String uid);

    @Query("UPDATE player_profiles SET " +
            "totalFirstServe=:totalFirstServe," +
            "totalSecondServe=:totalSecondServe," +
            "firstServeFault=:firstServeFault," +
            "secondServeFault=:secondServeFault," +
            "ace=:ace," +
            "aced=:aced," +
            "putAwayFailure=:putAwayOpportunity," +
            "putAwaySuccess=:putAwaySuccess," +
            "error=:error," +
            "defensiveTouch=:defensiveTouch," +
            "defensiveGet=:defensiveGet " +
            "WHERE uid=:PGPId;")
    void updatePGP(
            String PGPId,
            int totalFirstServe,
            int totalSecondServe,
            int firstServeFault,
            int secondServeFault,
            int ace,
            int aced,
            int putAwayOpportunity,
            int putAwaySuccess,
            int error,
            int defensiveTouch,
            int defensiveGet
    );
}
