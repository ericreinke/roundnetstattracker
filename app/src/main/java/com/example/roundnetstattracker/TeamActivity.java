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
        startActivityForResult(intent, 1);

    }

    // Call Back method, update teams if successful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            String message=data.getStringExtra("MESSAGE");
            System.out.println(message);
            updateTeams();
        }
    }
    public void updateTeams(){
        new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                final List<Team> allTeams = db.teamDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(allTeams.size() >= 2) {
                            firstTeamButton.setText(allTeams.get(0).name);
                            secondTeamButton.setText(allTeams.get(1).name);
                        }
                    }
                });
            }
        }).start();



    }

}
