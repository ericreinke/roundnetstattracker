package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.builders.PlayerBuilder;
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
import android.widget.Toast;

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
        // Replace the following line with a Toast saying we can't have an empty  name
        System.out.println("player: '"+playerNameEditText.getText().toString()+"'");
        if(playerNameEditText.getText().toString().equals("")) {
            Toast toast = new Toast(getApplicationContext());
            toast.makeText(this.getApplicationContext(),"Missing player name",Toast.LENGTH_LONG).show();
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                Player newPlayer = new PlayerBuilder()
                        .withName(playerNameEditText.getText().toString())
                        .build();
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.playerDao().insertAll(newPlayer);
            }
        }).start();
        Intent intent=new Intent();
        intent.putExtra("MESSAGE", "Inserted new player");
        setResult(1,intent);
        finish();
    }

}
