package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /*
    onClick method.
     */
    public void viewMyGames(View view){
        Intent intent = new Intent(HomeActivity.this, ViewGamesActivity.class);
        startActivity(intent);
    }

    /*
    onClick method.
     */
    public void deleteAllGames(View view){
        File dir = getFilesDir();
        File file = new File(dir,"games.json");
        File file2 = new File(dir, "idTracker.txt");
        file.delete();
        file2.delete();
    }

    /*
    onClick method.
     */
    public void newGame(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Intent intent = new Intent(HomeActivity.this,NewGameActivity.class);
        startActivity(intent);
    }

}
