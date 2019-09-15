package com.example.roundnetstattracker;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ViewGamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String fileName = "games.json";
        StringBuffer strBuffer = new StringBuffer();
        FileInputStream fis = null;

        try{
            fis = openFileInput(fileName);

            int read;
            while((read = fis.read()) != -1){
                strBuffer .append((char)read);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String jsonArrayOfGames = "{ \"games\": [" + strBuffer.toString() + "] }";
        System.out.println(jsonArrayOfGames);
        JsonObject obj = new JsonParser().parse(jsonArrayOfGames).getAsJsonObject();
        JsonArray arr = obj.getAsJsonArray("games");
        for(int i=0; i<arr.size(); i++){
            System.out.println("The score was: " + arr.get(i).getAsJsonObject().get("teamA").getAsJsonObject().get("score")
                    + " vs " + arr.get(i).getAsJsonObject().get("teamB").getAsJsonObject().get("score"));
            System.out.println("ID is: " + arr.get(i).getAsJsonObject().get("id"));
        }



    }

}
