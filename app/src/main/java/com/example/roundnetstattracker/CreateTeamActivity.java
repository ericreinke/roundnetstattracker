package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.builders.TeamBuilder;
import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.model.Team;
import com.example.roundnetstattracker.room.AppDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateTeamActivity extends AppCompatActivity {

    EditText teamNameTextView;
    Button addPlayerButton;
    TextView addPlayer1TextView;
    TextView addPlayer2TextView;
    String player1Id;
    String player2Id;

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

    public void addPlayer1OnClick(View view){
        Intent intent = new Intent(CreateTeamActivity.this, SelectPlayerActivity.class);
        intent.putExtra("PLAYER_NUMBER", 1);
        startActivityForResult(intent,1);
    }

    public void addPlayer2OnClick(View view){
        Intent intent = new Intent(CreateTeamActivity.this, SelectPlayerActivity.class);
        intent.putExtra("PLAYER_NUMBER", 2);
        startActivityForResult(intent,2);
    }

    // Call Back method, update teams if successful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;
        if(requestCode == 1) {
            player1Id = data.getStringExtra("PLAYER_ID");
            addPlayer1TextView.setText(data.getStringExtra("PLAYER_NAME"));
        }
        else if(requestCode == 2){
            player2Id = data.getStringExtra("PLAYER_ID");
            addPlayer2TextView.setText(data.getStringExtra("PLAYER_NAME"));
        }
    }

    public void createNewTeamOnClick(View view){

        // Replace the following if with a Toast "Please fill out appropriate things"
        if(teamNameTextView.getText().toString().equals("") ||
        addPlayer1TextView.getText().toString().equals("") ||
        addPlayer2TextView.getText().toString().equals("")){
            Toast toast = new Toast(getApplicationContext());
            toast.makeText(this.getApplicationContext(),"Missing fields",Toast.LENGTH_LONG).show();
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                Team newTeam = new TeamBuilder()
                        .withName(teamNameTextView.getText().toString())
                        .withPlayer1Id(player1Id)
                        .withPlayer2Id(player2Id)
                        .build();
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.teamDao().insertAll(newTeam);
            }
        }).start();
        Intent intent=new Intent();
        intent.putExtra("MESSAGE", "Inserted new team");
        setResult(1,intent);
        finish();
    }

}
