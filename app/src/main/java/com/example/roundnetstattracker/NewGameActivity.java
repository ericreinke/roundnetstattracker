package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String fileName = "idTracker.txt";
        StringBuffer strBuffer = new StringBuffer();
        FileInputStream fis = null;
        FileOutputStream fos = null;

        File dir = getFilesDir();
        File checkFile = new File(dir,fileName);

        // If tracker fil doesn't exist yet, initialize with "0"
        if(!checkFile.exists()){
            try {
                fos = openFileOutput(fileName, MODE_APPEND);
                fos.write("0".getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fos != null){
                    try{
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //Read the integer from idTracker;  strBuffer is next available id.
        try{
            fis = openFileInput(fileName);
            int read;
            while((read = fis.read()) != -1){
                strBuffer.append((char)read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fis != null){
                try{
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //Add 1 and re-write the tracker File
        try {
            fos = openFileOutput(fileName, MODE_PRIVATE);
            System.out.println("now writing: " + ((Integer.parseInt(strBuffer.toString()))+1));
            fos.write(Integer.toString((Integer.parseInt(strBuffer.toString()))+1).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fos != null){
                try{
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        final Game myGame = new Game("game"+strBuffer.toString());



        final EditText teamAEditText = findViewById(R.id.teamAPlainText);
        final EditText teamBEditText = findViewById(R.id.teamBPlainText);
        final EditText pA1EditText = findViewById(R.id.pA1TextView);
        final EditText pA2EditText = findViewById(R.id.pA2TextView);
        final EditText pB1EditText = findViewById(R.id.pB1TextView);
        final EditText pB2EditText = findViewById(R.id.pB2TextView);
        Button doneButton = findViewById(R.id.finishSetupButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myGame.teamA.setName(teamAEditText.getText().toString());
                myGame.teamB.setName(teamBEditText.getText().toString());
                myGame.teamA.player1.setName(pA1EditText.getText().toString());
                myGame.teamA.player2.setName(pA2EditText.getText().toString());
                myGame.teamB.player1.setName(pB1EditText.getText().toString());
                myGame.teamB.player2.setName(pB2EditText.getText().toString());

                Intent intent = new Intent(NewGameActivity.this, RecordGameActivity.class);
                intent.putExtra("GAME_OBJECT", myGame);
                startActivity(intent);

            }
        });

        teamAEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    pA1EditText.setHint(teamAEditText.getText() + " Player 1");
                    pA2EditText.setHint(teamAEditText.getText() + " Player 2");
                }
            }
        });
        teamBEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    pB1EditText.setHint(teamBEditText.getText() + " Player 1");
                    pB2EditText.setHint(teamBEditText.getText() + " Player 2");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
