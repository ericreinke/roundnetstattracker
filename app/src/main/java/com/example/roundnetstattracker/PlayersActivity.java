package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.recycler.PlayerRecyclerViewAdapter;
import com.example.roundnetstattracker.room.AppDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PlayersActivity extends AppCompatActivity {

    List<Player> allPlayers;
    PlayerRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allPlayers = new ArrayList<>();
        recyclerView = findViewById(R.id.playerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlayerRecyclerViewAdapter(this, allPlayers);
        recyclerView.setAdapter(adapter);

        updatePlayersRecycler();
    }

    public void createPlayerActivityOnClick(View view){
        Intent intent = new Intent(PlayersActivity.this, CreatePlayerActivity.class);
        startActivityForResult(intent, 1);
    }

    // Call Back method, update teams if successful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            updatePlayersRecycler();
        }
    }

    public void updatePlayersRecycler(){
        new Thread(new Runnable() {
            public void run() {
                allPlayers.add(new Player(false));
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                List<Player> allPlayersTemp = db.playerDao().getAll();
                //Not sure why, but we can't do `allPlayers = allPlayersTemp`  adding 1 by 1 works
                allPlayers.clear();
                allPlayers.addAll(allPlayersTemp);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}