package com.example.roundnetstattracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.room.AppDatabase;

public class TeamDetailsActivity extends AppCompatActivity {

    TextView teamDetailsTitleTextView;
    Team team;
    Player player1;
    Player player2;
    String teamIdFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamIdFromIntent = getIntent().getStringExtra("TEAM_ID");
        teamDetailsTitleTextView = findViewById(R.id.teamDetailsTitleTextView);

        new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                team = db.teamDao().getTeam(teamIdFromIntent);
                player1 = db.playerDao().getPlayer(team.player1Id);
                player2 = db.playerDao().getPlayer(team.player2Id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        teamDetailsTitleTextView.setText("team " + team.name + " has players: "+
                                player1.name + " (" + player1.uid +  ") and " +
                                player2.name + " (" + player2.uid);
                    }
                });
            }
        }).start();



    }

}
