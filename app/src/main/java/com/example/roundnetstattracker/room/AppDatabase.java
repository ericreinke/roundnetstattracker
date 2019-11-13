package com.example.roundnetstattracker.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.model.PlayerGameProfile;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.model.TeamGameProfile;

@Database(entities = {PlayerGameProfile.class , Player.class, TeamGameProfile.class, Team.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerProfileDAO playerProfileDao();
    public abstract TeamProfileDAO teamProfileDAO();
    public abstract TeamDAO teamDao();
    public abstract PlayerDAO playerDao();

    private static AppDatabase mMyDatabase;
    public static AppDatabase getInstance(Context context) {
        if(mMyDatabase==null){
            mMyDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "local_database").build();
            return mMyDatabase;
        }
        return mMyDatabase;
    }
}

