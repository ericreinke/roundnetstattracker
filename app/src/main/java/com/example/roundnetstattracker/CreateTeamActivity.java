package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.room.AppDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateTeamActivity extends AppCompatActivity {

    EditText teamNameTextView;
    Button addPlayerButton;
    TextView addPlayer1TextView;
    TextView addPlayer2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamNameTextView = findViewById(R.id.teamNameEditText);
        addPlayerButton = findViewById(R.id.addPlayer1Button);
        addPlayer1TextView = findViewById(R.id.addPlayer1TextView);
        addPlayer2TextView = findViewById(R.id.addPlayer2TextView);
    }

    public void addPlayerOnClick(View view){
        Intent intent = new Intent(CreateTeamActivity.this, SelectPlayerActivity.class);
        System.out.println("plz");
        startActivityForResult(intent,1);

    }

    // Call Back method, update teams if successful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("hell fucking o");
        if(requestCode == 1) {
            Player player1 = data.getParcelableExtra("PLAYER");
            System.out.println("??");
            if(player1 != null){
                System.out.println("did we make it");
                addPlayer1TextView.setText(player1.name);
            }
            Player player2 = data.getParcelableExtra("PLAYER2");
            if(player2 != null){
                addPlayer2TextView.setText(player2.name);
            }
        }
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
