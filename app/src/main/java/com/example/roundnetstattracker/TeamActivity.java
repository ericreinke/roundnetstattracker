package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.room.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import java.util.List;

public class TeamActivity extends AppCompatActivity {

    Button firstTeamButton;
    Button secondTeamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstTeamButton = findViewById(R.id.firstTeamButton);
        secondTeamButton= findViewById(R.id.secondTeamButton);
        updateTeams();
    }

    public void createTeamActivityOnClick(View view){
        Intent intent = new Intent(TeamActivity.this, CreateTeamActivity.class);
        startActivity(intent);

    }

    public void updateTeams(){
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        List<Team> allTeams = db.teamDao().getAll();
        firstTeamButton.setText(allTeams.get(0).name);
        secondTeamButton.setText(allTeams.get(1).name);
    }

}
