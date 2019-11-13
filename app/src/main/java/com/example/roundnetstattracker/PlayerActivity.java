package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.room.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    Button firstPlayerButton = findViewById(R.id.firstPlayerButton);
    Button secondPlayerButton = findViewById(R.id.secondPlayerButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updatePlayers();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void createPlayerActivityOnClick(View view){
        Intent intent = new Intent(PlayerActivity.this, CreatePlayerActivity.class);
        startActivity(intent);
        updatePlayers();
    }

    private void updatePlayers(){
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        List<Player> allPlayers = db.playerDao().getAll();
        firstPlayerButton.setText(allPlayers.get(0).getName());
        secondPlayerButton.setText(allPlayers.get(1).getName());
    }

}
