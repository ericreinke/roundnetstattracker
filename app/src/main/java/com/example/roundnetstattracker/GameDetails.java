package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Game;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Game theGame = getIntent().getExtras().getParcelable("GAME_OBJECT");

        TextView gameDetailsGameName = findViewById(R.id.gameDetailsGameNameTextView);
        gameDetailsGameName.setText(theGame.teamGameProfileA.name + "vs" + theGame.teamGameProfileB);
    }

    /*
    On click method
     */
    public void returnHome(View view){
        Intent intent = new Intent(GameDetails.this, HomeActivity.class);
        startActivity(intent);
    }

}
