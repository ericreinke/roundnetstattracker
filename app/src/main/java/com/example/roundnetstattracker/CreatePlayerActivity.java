package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.room.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreatePlayerActivity extends AppCompatActivity {

    EditText playerNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playerNameEditText = findViewById(R.id.playerNameEditText);
    }

    public void createNewPlayerOnClick(View view){
        new Thread(new Runnable() {
            public void run() {
                Player newPlayer = new Player(playerNameEditText.getText().toString());
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                System.out.println("inserting");
                db.playerDao().insertAll(newPlayer);
            }
        }).start();
        Intent intent=new Intent();
        intent.putExtra("MESSAGE", "Inserted new player");
        setResult(1,intent);
        finish();
    }

}
