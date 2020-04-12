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

import org.w3c.dom.Text;

import java.util.Locale;

public class GameDetailsActivity extends AppCompatActivity {

    private TeamGameProfile teamAProfile;
    private TeamGameProfile teamBProfile;
    private PlayerGameProfile playerA1Profile;
    private PlayerGameProfile playerA2Profile;
    private PlayerGameProfile playerB1Profile;
    private PlayerGameProfile playerB2Profile;
    private PlayerGameProfile [] allPGP;
    private TextView tableName0TextView;
    private TextView tableName1TextView;
    private TextView tableName2TextView;
    private TextView tableName3TextView;
    private TextView tableServe0TextView;
    private TextView tableServe1TextView;
    private TextView tableServe2TextView;
    private TextView tableServe3TextView;
    private TextView tablePutaway0TextView;
    private TextView tablePutaway1TextView;
    private TextView tablePutaway2TextView;
    private TextView tablePutaway3TextView;
    private TextView tableAce0TextView;
    private TextView tableAce1TextView;
    private TextView tableAce2TextView;
    private TextView tableAce3TextView;
    private TextView tableError0TextView;
    private TextView tableError1TextView;
    private TextView tableError2TextView;
    private TextView tableError3TextView;
    private TextView tableDefense0TextView;
    private TextView tableDefense1TextView;
    private TextView tableDefense2TextView;
    private TextView tableDefense3TextView;

    private TextView [] nameCells;
    private TextView [] serveCells;
    private TextView [] putawayCells;
    private TextView [] errorCells;
    private TextView [] aceCells;
    private TextView [] defenseCells;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamAProfile = getIntent().getParcelableExtra("TEAM_A");
        teamBProfile = getIntent().getParcelableExtra("TEAM_B");
        assert(teamAProfile!=null);
        assert(teamBProfile!=null);

        tableName0TextView = findViewById(R.id.tableName0TextView);
        tableName1TextView = findViewById(R.id.tableName1TextView);
        tableName2TextView = findViewById(R.id.tableName2TextView);
        tableName3TextView = findViewById(R.id.tableName3TextView);
        tableServe0TextView = findViewById(R.id.tableServe0TextView);
        tableServe1TextView = findViewById(R.id.tableServe1TextView);
        tableServe2TextView = findViewById(R.id.tableServe2TextView);
        tableServe3TextView = findViewById(R.id.tableServe3TextView);
        tablePutaway0TextView = findViewById(R.id.tablePutaway0TextView);
        tablePutaway1TextView = findViewById(R.id.tablePutaway1TextView);
        tablePutaway2TextView = findViewById(R.id.tablePutaway2TextView);
        tablePutaway3TextView = findViewById(R.id.tablePutaway3TextView);
        tableAce0TextView = findViewById(R.id.tableAce0TextView);
        tableAce1TextView = findViewById(R.id.tableAce1TextView);
        tableAce2TextView = findViewById(R.id.tableAce2TextView);
        tableAce3TextView = findViewById(R.id.tableAce3TextView);
        tableError0TextView = findViewById(R.id.tableError0TextView);
        tableError1TextView = findViewById(R.id.tableError1TextView);
        tableError2TextView = findViewById(R.id.tableError2TextView);
        tableError3TextView = findViewById(R.id.tableError3TextView);
        tableDefense0TextView = findViewById(R.id.tableDefense0TextView);
        tableDefense1TextView = findViewById(R.id.tableDefense1TextView);
        tableDefense2TextView = findViewById(R.id.tableDefense2TextView);
        tableDefense3TextView = findViewById(R.id.tableDefense3TextView);

        nameCells = new TextView[] {tableName0TextView, tableName1TextView, tableName2TextView, tableName3TextView};
        serveCells = new TextView[] {tableServe0TextView, tableServe1TextView, tableServe2TextView, tableServe3TextView};
        putawayCells = new TextView[] {tablePutaway0TextView, tablePutaway1TextView, tablePutaway2TextView, tablePutaway3TextView};
        aceCells = new TextView[] {tableAce0TextView, tableAce1TextView, tableAce2TextView, tableAce3TextView};
        defenseCells = new TextView[] {tableDefense0TextView, tableDefense1TextView, tableDefense2TextView, tableDefense3TextView};
        errorCells = new TextView[] {tableError0TextView, tableError1TextView, tableError2TextView, tableError3TextView};

        Thread t = new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                playerA1Profile = db.playerProfileDao().getPlayerProfile(teamAProfile.playerGameProfile1Id);
                playerA2Profile = db.playerProfileDao().getPlayerProfile(teamAProfile.playerGameProfile2Id);
                playerB1Profile = db.playerProfileDao().getPlayerProfile(teamBProfile.playerGameProfile1Id);
                playerB2Profile = db.playerProfileDao().getPlayerProfile(teamBProfile.playerGameProfile2Id);
                allPGP = new PlayerGameProfile[] {playerA1Profile, playerA2Profile, playerB1Profile, playerB2Profile};
            }
        });
        t.start();
        try{
            t.join();
        }catch(InterruptedException e){
            //handle
        }

        // Populate table:

        for(int i = 0; i < allPGP.length; i++){
            nameCells[i].setText(allPGP[i].playerName);
            serveCells[i].setText(String.format(Locale.getDefault(), "%.0f%% : %.0f%%\n%.0f%% : %.0f%%",
                    allPGP[i].totalFirstServe == 0 ? 0 :
                            100*((float)1 - (float)allPGP[i].secondServeFault/Math.max(1, allPGP[i].totalFirstServe)),
                    (allPGP[i].totalFirstServe + allPGP[i].totalSecondServe) == 0 ? 0 :
                            100*((float)1 - (float)(allPGP[i].firstServeFault + allPGP[i].secondServeFault)/
                            Math.max(1,(allPGP[i].totalFirstServe + allPGP[i].totalSecondServe))),
                    allPGP[i].totalFirstServe == 0 ? 0 :
                            100*((float)1 - (float)allPGP[i].firstServeFault/Math.max(1, allPGP[i].totalFirstServe)),
                    allPGP[i].totalSecondServe == 0 ? 0 :
                            100*((float)1 - (float)allPGP[i].secondServeFault/Math.max(1, allPGP[i].totalSecondServe))));
            putawayCells[i].setText(String.format(Locale.getDefault(),"%.0f%%",100* (float)allPGP[i].putAwaySuccess/
                    Math.max(1, (allPGP[i].putAwaySuccess + allPGP[i].putAwayFailure))));
            aceCells[i].setText(String.format(Locale.getDefault(), "%d : %d", allPGP[i].ace, allPGP[i].aced));
            errorCells[i].setText(String.format(Locale.getDefault(), "%d", allPGP[i].error));
            defenseCells[i].setText(String.format(Locale.getDefault(), "%d", allPGP[i].defensiveGet));
        }

    }

    /*
    On click method
     */
    public void returnHome(View view) {
        Intent intent = new Intent(GameDetailsActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
