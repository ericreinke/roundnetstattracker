package com.example.roundnetstattracker;

import android.os.Bundle;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.room.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class CreatePlayerActivity extends AppCompatActivity {

    TextView playerNameTextView = findViewById(R.id.playerNameTextView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void createNewTeamOnClick(View view){
        Player player = new Player((String)playerNameTextView.getText());
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());

        db.playerDao().insertAll(player);
    }

}
