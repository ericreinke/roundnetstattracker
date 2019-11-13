package com.example.roundnetstattracker;

import android.os.Bundle;

import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.room.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

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
        Team newTeam = new Team((String)teamNameTextView.getText());
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());

        db.teamDao().insertAll(newTeam);
    }

}
