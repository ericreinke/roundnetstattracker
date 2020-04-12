package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.roundnetstattracker.builders.GameBuilder;
import com.example.roundnetstattracker.builders.PlayerGameProfileBuilder;
import com.example.roundnetstattracker.builders.TeamGameProfileBuilder;
import com.example.roundnetstattracker.model.Game;
import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.model.PlayerGameProfile;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.model.TeamGameProfile;
import com.example.roundnetstattracker.room.AppDatabase;

import java.util.List;

/*
 * NOTHING GETS INSERTED INTO 'games' 'player_profiles' OR 'team_profiles
 * IN THIS ACTIVITY.  THESE ARE ONLY INSERTED IN RecordGameActivity
 */
public class CreateGameActivity extends AppCompatActivity {

    String teamAId;
    String teamBId;
    Team teamA;
    Team teamB;
    Player pA1;
    Player pA2;
    Player pB1;
    Player pB2;
    TextView teamATextView;
    TextView teamBTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamATextView = findViewById(R.id.teamATextView);
        teamBTextView = findViewById(R.id.teamBTextView);
    }

    public void addTeam1OnClick(View view){
        Intent intent = new Intent(CreateGameActivity.this, SelectTeamActivity.class);
        intent.putExtra("TEAM_NUMBER", 1);
        startActivityForResult(intent,1);
    }

    public void addTeam2OnClick(View view){
        Intent intent = new Intent(CreateGameActivity.this, SelectTeamActivity.class);
        intent.putExtra("TEAM_NUMBER", 2);
        startActivityForResult(intent,2);
    }

    // Call Back method, update teams if successful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            Toast toast = new Toast(getApplicationContext());
            toast.makeText(this.getApplicationContext(),"Did not receive any team data",Toast.LENGTH_LONG).show();
            return;
        }
        else if(requestCode == 1) {
            teamAId = data.getStringExtra("TEAM_ID");
            teamATextView.setText(data.getStringExtra("TEAM_NAME"));
            new Thread(new Runnable() {
                public void run() {
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                    teamA = db.teamDao().getTeam(teamAId);
                    pA1 = db.playerDao().getPlayer(teamA.player1Id);
                    pA2 = db.playerDao().getPlayer(teamA.player2Id);
                }
            }).start();
        }
        else if(requestCode == 2){
            teamBId = data.getStringExtra("TEAM_ID");
            teamBTextView.setText(data.getStringExtra("TEAM_NAME"));
            new Thread(new Runnable() {
                public void run() {
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                    teamB = db.teamDao().getTeam(teamBId);
                    pB1 = db.playerDao().getPlayer(teamB.player1Id);
                    pB2 = db.playerDao().getPlayer(teamB.player2Id);
                }
            }).start();
        }
    }

    public void doneOnClick (View view){//
        if(teamA == null || teamB == null){
            Toast toast = new Toast(getApplicationContext());
            toast.makeText(this.getApplicationContext(),"Missing one or more team data",Toast.LENGTH_LONG).show();
            return;
        }
        if(teamA.uid == teamB.uid){
            Toast toast = new Toast(getApplicationContext());
            toast.makeText(this.getApplicationContext(),"Team 1 and 2 are identical",Toast.LENGTH_LONG).show();
            return;
        }
        //Validation in CreateTeamActivity ensures players on a team are unique
        if(pA1.uid.equals(pB1.uid) || pA1.uid.equals(pB2.uid) || pA2.uid.equals(pB1.uid)|| pA2.uid.equals(pB2.uid)){
            Toast toast = new Toast(getApplicationContext());
            toast.makeText(this.getApplicationContext(),"One or more players are both teams",Toast.LENGTH_LONG).show();
            return;
        }

        PlayerGameProfile ppA1 = new PlayerGameProfileBuilder()
                .withPlayerId(pA1.uid)
                .withPlayerName(pA1.name)
                .build();
        PlayerGameProfile ppA2 = new PlayerGameProfileBuilder()
                .withPlayerId(pA2.uid)
                .withPlayerName(pA2.name)
                .build();
        PlayerGameProfile ppB1 = new PlayerGameProfileBuilder()
                .withPlayerId(pB1.uid)
                .withPlayerName(pB1.name)
                .build();
        PlayerGameProfile ppB2 = new PlayerGameProfileBuilder()
                .withPlayerId(pB2.uid)
                .withPlayerName(pB2.name)
                .build();

        TeamGameProfile teamAProfile = new TeamGameProfileBuilder()
                .withTeamId(teamAId)
                .withPlayerGameProfile1Id(ppA1.uid)
                .withPlayerGameProfile2Id(ppA2.uid)
                .withTeamName(teamA.name)
                .build();
        TeamGameProfile teamBProfile = new TeamGameProfileBuilder()
                .withTeamId(teamBId)
                .withPlayerGameProfile1Id(ppB1.uid)
                .withPlayerGameProfile2Id(ppB2.uid)
                .withTeamName(teamB.name)
                .build();

        Game myGame = new GameBuilder()
                .withTeamAId(teamAId)
                .withTeamBId(teamBId)
                .withTeamAProfileId(teamAProfile.uid)
                .withTeamBProfileId(teamBProfile.uid)
                .build();

        Intent intent = new Intent(CreateGameActivity.this, RecordGameActivity.class);
        intent.putExtra("GAME_OBJECT", myGame);
        intent.putExtra("TEAM_A_PROFILE", teamAProfile);
        intent.putExtra("TEAM_B_PROFILE", teamBProfile);
        intent.putExtra("PLAYER_A_1_PROFILE",ppA1);
        intent.putExtra("PLAYER_A_2_PROFILE",ppA2);
        intent.putExtra("PLAYER_B_1_PROFILE",ppB1);
        intent.putExtra("PLAYER_B_2_PROFILE",ppB2);

        startActivity(intent);
        finish();
    }
}
