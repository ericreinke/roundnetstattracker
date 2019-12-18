package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.recycler.SelectTeamRecyclerViewAdapter;
import com.example.roundnetstattracker.room.AppDatabase;
import com.example.roundnetstattracker.room.OnRecyclerViewTeamClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectTeamActivity extends AppCompatActivity implements OnRecyclerViewTeamClickListener {

    List<Team> allTeams;
    SelectTeamRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    int teamNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allTeams = new ArrayList<>();
        recyclerView = findViewById(R.id.selectTeamRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectTeamRecyclerViewAdapter(this, allTeams, this);
        recyclerView.setAdapter(adapter);
        teamNumber = getIntent().getIntExtra("TEAM_NUMBER", -1);

        updateTeamsRecycler();

    }

    public void updateTeamsRecycler(){
        new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                List<Team> allTeamsTemp = db.teamDao().getAll();
                // Not sure why, but we can't do `allTeams = allTeamsTemp' or
                // 'allTeams = db.tD().getAll()`  adding 1 by 1 works
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

    public void onTeamClick(Team team){
        Intent i = new Intent();
        i.putExtra("TEAM_NAME", team.name);
        i.putExtra("TEAM_ID", team.uid);
        setResult(teamNumber, i);
        finish();

    }

    public void createNewTeamOnClick(View view){
        Intent i = new Intent(SelectTeamActivity.this, CreateTeamActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;
        updateTeamsRecycler();
    }

}
