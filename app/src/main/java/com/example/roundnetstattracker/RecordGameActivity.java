package com.example.roundnetstattracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.TextView;

import com.example.roundnetstattracker.model.Game;

public class RecordGameActivity extends AppCompatActivity {

    TextView teamATextView;
    TextView teamBTextView;
    TextView instructionTextView;
    TextView teamAScoreTextView;
    TextView teamBScoreTextView;
    Button teamAButton1;
    Button teamBButton1;
    Button teamAButton2;
    Button teamBButton2;
    Button saveButton;
    String [] playerNames = new String[4];
    String [] teamNames = new String[2];
    int [] firstServers = new int[2]; //index is the team, value is player
    boolean [] whoServed = new boolean[4];
    int nextServerTeam1 = -1;
    int nextServerTeam2 = -1;
    Game myGame = null;
    int teamPossession = -1; //0 or 1;
    int serveAttempt = 1; //1 or 2;
    StringBuilder rallyBuilder = new StringBuilder();

    /*  0: determine server1
     *   1: determine server2  //THIS MIGHT BE DEPRECATED
     *   2: "player is now serving" waiting for serve result
     *   3: waiting for touches
     *   4: ask for putaway/error
     */
    int state = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamATextView = findViewById(R.id.recordTeamATextView);
        teamBTextView = findViewById(R.id.recordTeamBTextView);
        instructionTextView = findViewById(R.id.instructionTextView);
        teamAScoreTextView = findViewById(R.id.recordTeamATextView);
        teamBScoreTextView = findViewById(R.id.recordTeamBTextView);
        teamAButton1 = findViewById(R.id.teamAButton1);
        teamBButton1 = findViewById(R.id.teamBButton1);
        teamAButton2 = findViewById(R.id.teamAButton2);
        teamBButton2 = findViewById(R.id.teamBButton2);
        saveButton = findViewById(R.id.saveButton);

    }




}
