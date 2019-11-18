package com.example.roundnetstattracker;

import android.os.Bundle;

import com.example.roundnetstattracker.model.Game;
import com.example.roundnetstattracker.recycler.GameRecyclerViewAdapter;
import com.example.roundnetstattracker.room.AppDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        String fileName = "games.json";
//        StringBuffer strBuffer = new StringBuffer();
//        FileInputStream fis = null;
//
//        try{
//            fis = openFileInput(fileName);
//
//            int read;
//            while((read = fis.read()) != -1){
//                strBuffer .append((char)read);
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        String jsonArrayOfGames = "{ \"games\": [" + strBuffer.toString() + "] }";
//        System.out.println(jsonArrayOfGames);
//        JsonObject obj = new JsonParser().parse(jsonArrayOfGames).getAsJsonObject();
//        JsonArray arr = obj.getAsJsonArray("games");
//        for(int i=0; i<arr.size(); i++){
//            System.out.println("The score was: " + arr.get(i).getAsJsonObject().get("teamGameProfileA").getAsJsonObject().get("score")
//                    + " vs " + arr.get(i).getAsJsonObject().get("teamGameProfileB").getAsJsonObject().get("score"));
//            System.out.println("ID is: " + arr.get(i).getAsJsonObject().get("id"));
//        }

        new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                System.out.println("inserting");
                final List<Game> allGames = db.gameDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView = findViewById(R.id.gameRecyclerView);
                        GameRecyclerViewAdapter adapter = new GameRecyclerViewAdapter(GamesActivity.this, allGames);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(GamesActivity.this));
                    }
                });
            }
        }).start();

    }

}
