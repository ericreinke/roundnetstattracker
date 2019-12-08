package com.example.roundnetstattracker;

import android.os.Bundle;

import com.example.roundnetstattracker.model.Game;
import com.example.roundnetstattracker.model.TeamGameProfile;
import com.example.roundnetstattracker.recycler.GameRecyclerViewAdapter;
import com.example.roundnetstattracker.room.AppDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                final List<Game> allGames = db.gameDao().getAll();
                final List<TeamGameProfile> allTeamAProfiles = new ArrayList<>();
                final List<TeamGameProfile> allTeamBProfiles = new ArrayList<>();
                System.out.println(allGames.size());
                for(int i = 0; i < allGames.size(); i++){
                    allTeamAProfiles.add(db.teamProfileDAO().getTeamProfile(allGames.get(i).teamAProfileId));
                    allTeamBProfiles.add(db.teamProfileDAO().getTeamProfile(allGames.get(i).teamBProfileId));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView = findViewById(R.id.gameRecyclerView);
                        GameRecyclerViewAdapter adapter = new GameRecyclerViewAdapter(GamesActivity.this, allTeamAProfiles, allTeamBProfiles);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(GamesActivity.this));
                    }
                });
            }
        }).start();
    }

}
