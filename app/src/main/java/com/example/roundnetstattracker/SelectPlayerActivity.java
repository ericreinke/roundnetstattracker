package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.roundnetstattracker.model.Player;
import com.example.roundnetstattracker.recycler.SelectPlayerRecyclerViewAdapter;
import com.example.roundnetstattracker.room.AppDatabase;
import com.example.roundnetstattracker.room.OnRecyclerViewPlayerClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectPlayerActivity extends AppCompatActivity implements OnRecyclerViewPlayerClickListener {

    List<Player> allPlayers;
    SelectPlayerRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    int playerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allPlayers = new ArrayList<>();
        recyclerView = findViewById(R.id.selectPlayerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectPlayerRecyclerViewAdapter(this, allPlayers, this);
        recyclerView.setAdapter(adapter);
        playerNumber = getIntent().getIntExtra("PLAYER_NUMBER", -1);

        updatePlayersRecycler();
    }


    public void updatePlayersRecycler(){
        new Thread(new Runnable() {
            public void run() {
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

    @Override
    public void onPlayerClick(Player player){
        Intent i =new Intent();
        i.putExtra("PLAYER_ID", player.uid);
        i.putExtra("PLAYER_NAME", player.name);
        setResult(playerNumber,i);
        finish();
    }
}
