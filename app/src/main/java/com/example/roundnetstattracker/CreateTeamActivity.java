package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.room.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class CreateTeamActivity extends AppCompatActivity {


    TextView teamNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamNameTextView = findViewById(R.id.teamNameEditText);
    }

    public void createNewTeamOnClick(View view){
        new Thread(new Runnable() {
            public void run() {
                Team newTeam = new Team(teamNameTextView.getText().toString());
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                System.out.println("inserting");
                db.teamDao().insertAll(newTeam);
            }
        }).start();
        Intent intent=new Intent();
        intent.putExtra("MESSAGE", "Inserted new team");
        setResult(1,intent);
        finish();
    }

}
