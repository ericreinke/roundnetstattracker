package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.recycler.TeamRecyclerViewAdapter;
import com.example.roundnetstattracker.room.AppDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TeamsActivity extends AppCompatActivity{

    List<Team> allTeams;
    TeamRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allTeams = new ArrayList<>();
        recyclerView = findViewById(R.id.teamRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamRecyclerViewAdapter(this, allTeams);
        recyclerView.setAdapter(adapter);

        updateTeamsRecycler();
    }

    public void createTeamActivityOnClick(View view){
        Intent intent = new Intent(TeamsActivity.this, CreateTeamActivity.class);
        startActivityForResult(intent, 1);
    }

    // Call Back method, update teams if successful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            String message=data.getStringExtra("MASSAGE");
            System.out.println(message);
            updateTeamsRecycler();
        }
    }

    public void updateTeamsRecycler(){
        new Thread(new Runnable() {
            public void run() {
                allTeams.add(new Team(false));
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                List<Team> allTeamsTemp = db.teamDao().getAll();
                //Not sure why, but we can't do `allTeams = allTeamsTemp`  adding 1 by 1 works
                allTeams.clear();
                allTeams.addAll(allTeamsTemp);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }


}