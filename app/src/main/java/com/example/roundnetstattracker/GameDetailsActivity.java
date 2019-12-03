package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.builders.TeamBuilder;
import com.example.roundnetstattracker.model.Game;
import com.example.roundnetstattracker.model.PlayerGameProfile;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.model.TeamGameProfile;
import com.example.roundnetstattracker.room.AppDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class GameDetailsActivity extends AppCompatActivity {

    Game game = null;
    PlayerGameProfile playerA1Profile;
    PlayerGameProfile playerA2Profile;
    PlayerGameProfile playerB1Profile;
    PlayerGameProfile playerB2Profile;
    PlayerGameProfile [] allPGP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        game = getIntent().getParcelableExtra("GAME");
        assert(game!=null);

        new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                TeamGameProfile TGPa = db.teamProfileDAO().getTeamProfile(game.teamAProfileId);
                TeamGameProfile TGPb = db.teamProfileDAO().getTeamProfile(game.teamBProfileId);
                playerA1Profile = db.playerProfileDao().getPlayerProfile(TGPa.playerGameProfile1Id);
                playerA2Profile = db.playerProfileDao().getPlayerProfile(TGPa.playerGameProfile2Id);
                playerB1Profile = db.playerProfileDao().getPlayerProfile(TGPb.playerGameProfile1Id);
                playerB2Profile = db.playerProfileDao().getPlayerProfile(TGPb.playerGameProfile2Id);
                allPGP = new PlayerGameProfile[] {playerA1Profile, playerA2Profile, playerB1Profile, playerB2Profile};
            }
        }).start();

        TextView gameDetailsGameName = findViewById(R.id.gameDetailsGameNameTextView);
    }

    /*
    On click method
     */
    public void returnHome(View view){
        Intent intent = new Intent(GameDetailsActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
