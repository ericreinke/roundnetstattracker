package com.example.roundnetstattracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecordGameActivity extends AppCompatActivity {

    TextView teamATextView;
    TextView teamBTextView;
    TextView recordPA1TextView;
    TextView recordPA2TextView ;
    TextView recordPB1TextView;
    TextView recordPB2TextView;
    TextView instructionTextView;
    TextView teamAScoreTextView;
    TextView teamBScoreTextView;
    Button teamAButton1;
    Button teamBButton1;
    Button teamAButton2;
    Button teamBButton2;
    Button saveButton;
    String [] playerNames = new String[4];
    String [] teamNames = new String[2];
    int [] firstServers = new int[2]; //index is the team, value is player
    boolean [] whoServed = new boolean[4];
    int nextServerTeam1 = -1;
    int nextServerTeam2 = -1;
    Game myGame = null;
    int teamPossession = -1; //0 or 1;
    int serveAttempt = 1; //1 or 2;
    StringBuilder rallyBuilder = new StringBuilder();

    /*  0: determine server1
     *   1: determine server2  //THIS MIGHT BE DEPRECATED
     *   2: "player is now serving" waiting for serve result
     *   3: waiting for touches
     *   4: ask for putaway/error
     */
    int state = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamATextView = findViewById(R.id.recordTeamATextView);
        teamBTextView = findViewById(R.id.recordTeamBTextView);
        recordPA1TextView = findViewById(R.id.recordPA1TextView);
        recordPA2TextView = findViewById(R.id.recordPA2TextView);
        recordPB1TextView = findViewById(R.id.recordPB1TextView);
        recordPB2TextView = findViewById(R.id.recordPB2TextView);
        instructionTextView = findViewById(R.id.instructionTextView);
        teamAScoreTextView = findViewById(R.id.teamAScoreTextView);
        teamBScoreTextView = findViewById(R.id.teamBScoreTextView);
        teamAButton1 = findViewById(R.id.teamAButton1);
        teamBButton1 = findViewById(R.id.teamBButton1);
        teamAButton2 = findViewById(R.id.teamAButton2);
        teamBButton2 = findViewById(R.id.teamBButton2);
        saveButton = findViewById(R.id.saveButton);

        myGame = (Game) getIntent().getExtras().getParcelable("GAME_OBJECT");
        playerNames[0] = myGame.teamA.player1.getName();
        playerNames[1] = myGame.teamA.player2.getName();
        playerNames[2] = myGame.teamB.player1.getName();
        playerNames[3] = myGame.teamB.player2.getName();
        teamNames[0] = myGame.teamA.getName();
        teamNames[1] = myGame.teamB.getName();
        firstServers[0] =- 1;
        firstServers[1] = -1;
        whoServed[0] = false;
        whoServed[1] = false;
        whoServed[2] = false;
        whoServed[3] = false;

        teamATextView.setText(myGame.teamA.getName());
        teamBTextView.setText(myGame.teamB.getName());
        teamAScoreTextView.setText(Integer.toString(myGame.teamA.getScore()));
        teamBScoreTextView.setText(Integer.toString(myGame.teamB.getScore()));
        recordPA1TextView.setText(myGame.teamA.player1.getName());
        recordPA2TextView.setText(myGame.teamA.player2.getName());
        recordPB1TextView.setText(myGame.teamB.player1.getName());
        recordPB2TextView.setText(myGame.teamB.player2.getName());

        instructionTextView.setText(R.string.who_serve_tool_tip);
        teamAButton1.setText(playerNames[0]);
        teamAButton2.setText(playerNames[1]);
        teamBButton1.setText(playerNames[2]);
        teamBButton2.setText(playerNames[3]);

        state  = 2;

        askForServer();

        teamAButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(state){
                    case(0): //determine server ; playerA1 button
                        firstServers[0] = 0;
                        teamPossession=0;
                        isServing(0);
                        nextServerTeam1 = 1;
                        setupStateServing();
                        break;
                    case(2): //serving state ; clean button
                        teamPossession ^= 1; //change possession first
                        rallyBuilder.append("n");
                        setupStateTouch(teamPossession);
                        break;

                    case(3): //player1 on team=teamPossession touch;
                        rallyBuilder.append(teamPossession*2);
                        break;
                    case(4): //someone put it away

//                        String lookingFor = "N";
//                        if(rallyBuilder.lastIndexOf(lookingFor)<0) {
//                            lookingFor = "n";
//                        }
//                        int spiker = Integer.parseInt( rallyBuilder.charAt(rallyBuilder.lastIndexOf(lookingFor)-1) + "");
                        rallyBuilder.append("P");
                        myGame.addRally(rallyBuilder+"");
                        rallyBuilder.delete(0,rallyBuilder.length());
                        serveAttempt = 1;
                        setupStateServing();
                }
                System.out.println(rallyBuilder);
            }
        });
        teamAButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(state){
                    case(0): //determine server1 ; playerA1 button
                        isServing(1);
                        nextServerTeam1 = 0;
                        teamPossession = 0;
                        firstServers[0] = 1;
                        setupStateServing();
                        break;
                    case(2):
                        //This case should never happen because tAB2 is GONE in state 2
                        break;

                    case(3): //ball off the net.  change of possession
                        rallyBuilder.append("N");
                        changePossessionState();
                        break;
                }
                System.out.println(rallyBuilder);
            }
        });
        teamBButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(state){
                    case(0): //determine server1 ; playerA1 button
                        isServing(2);
                        nextServerTeam2 = 3;
                        teamPossession = 1;
                        firstServers[1] = 2;
                        setupStateServing();
                        break;
                    case(2):// service fault button
                        if(serveAttempt == 1){
                            rallyBuilder.append("F");
                            serveAttempt = 2;
                            setupStateServing();
                        }
                        else if(serveAttempt == 2){
                            rallyBuilder.append("f");
                            System.out.println(teamPossession);
                            if(teamPossession == 0){
                                myGame.teamB.addScore(1);
                                teamBScoreTextView.setText(Integer.toString(myGame.teamB.getScore()));
                                if((whoServed[0] || whoServed[1]) && nextServerTeam2 != -1){ //new server
                                    isServing(nextServerTeam2);
                                    nextServerTeam2 -= 2;  // this will flip the sever
                                    nextServerTeam2 ^= 1;
                                    nextServerTeam2 +=2;
                                }
                            }
                            else if(teamPossession == 1){
                                myGame.teamA.addScore(1);
                                teamAScoreTextView.setText(Integer.toString(myGame.teamA.getScore()));
                                if((whoServed[2] || whoServed[3]) && nextServerTeam1 != -1){ //new server
                                    isServing(nextServerTeam1);
                                    nextServerTeam1 ^= 1;
                                }
                            }
                            myGame.addRally(rallyBuilder+"");
                            rallyBuilder.delete(0,rallyBuilder.length());
                            serveAttempt = 1;
                            teamPossession^=1;
                            setupStateServing();
                        }

                        break;

                    case(3):
                        rallyBuilder.append(teamPossession*2+1);
                        break;

                    case(4): //team somcething palyer1 error
                        rallyBuilder.append("E");
                        rallyBuilder.append(teamPossession*2);
                        myGame.addRally(rallyBuilder+"");
                        rallyBuilder.delete(0,rallyBuilder.length());
                        serveAttempt = 1;
                        setupStateServing();
                }
                System.out.println(rallyBuilder);

            }
        });
        teamBButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(state){
                    case(0): //determine server1 ; playerA1 button
                        isServing(3);
                        nextServerTeam2 = 2;
                        teamPossession = 1;
                        firstServers[1]=3;
                        setupStateServing();
                        break;
                    case(2):
                        //This case should never happen because tAB2 and tBB2 are GONE in state 2
                        break;

                    case(3): // play is over. ball hit ground/rim
                        if(teamPossession==0){
                            myGame.teamB.addScore(1);
                            teamBScoreTextView.setText(Integer.toString(myGame.teamB.getScore()));
                            if((whoServed[0] || whoServed[1]) && nextServerTeam2 != -1){ //new server
                                 isServing(nextServerTeam2);
                                 nextServerTeam2 -= 3;  // this will flip the sever
                                 nextServerTeam2 ^= 1;
                                 nextServerTeam2 +=3;
                            }
                        }
                        else if(teamPossession == 1){
                            myGame.teamA.addScore(1);
                            teamAScoreTextView.setText(Integer.toString(myGame.teamA.getScore()));
                            if((whoServed[2] || whoServed[3]) && nextServerTeam1 != -1){ //new server
                                isServing(nextServerTeam1);
                                nextServerTeam1 ^= 1;
                            }
                        }
                        teamPossession ^= 1;
                        teamAScoreTextView.setText(Integer.toString(myGame.teamA.getScore()));
                        teamBScoreTextView.setText(Integer.toString(myGame.teamB.getScore()));
                        askForPutawayError();

                        break;
                    case(4): //team somcething palyer1 error
                        rallyBuilder.append("E");
                        rallyBuilder.append(teamPossession*2);
                        serveAttempt = 1;
                        myGame.addRally(rallyBuilder+"");
                        rallyBuilder.delete(0,rallyBuilder.length());
                        setupStateServing();

                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                String myGameJson = gson.toJson(myGame);
                System.out.println(myGameJson);

                String fileName = "games.json";
                FileOutputStream fos = null;

                File dir = getFilesDir();
                File checkFile = new File(dir,fileName);

                if(checkFile.exists()){
                    myGameJson = ", " + myGameJson;
                }


                try {
                    fos = openFileOutput(fileName, MODE_APPEND);
                    fos.write(myGameJson.getBytes());
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

                Intent intent = new Intent(RecordGameActivity.this, GameDetails.class);
                intent.putExtra("GAME_OBJECT", myGame);
                //We need to either pass the Game object or the ID here.  Game object is probbably eaiser
                startActivity(intent);
            }
        });
    }

    public void askForPutawayError(){
        //TEAM POSSESSION IS CURRENTLY THE TEAM WHO SCORED
        System.out.println("possession is team: " + teamPossession);
        serveAttempt = 1;
        String lookingFor = "N";
        if(rallyBuilder.lastIndexOf(lookingFor)<0){
            lookingFor = "n";
        }
        System.out.println(rallyBuilder);
        System.out.println(rallyBuilder.charAt(rallyBuilder.lastIndexOf(lookingFor)-1) + "");
        int spiker = Integer.parseInt( rallyBuilder.charAt(rallyBuilder.lastIndexOf(lookingFor)-1) + "");
        instructionTextView.setText("Did "+ playerNames[spiker] + " put it away/ace?\nOr did " +
                teamNames[teamPossession^1] + " make an unforced Error?");

        teamAButton1.setText(playerNames[spiker] + " put-away");
        teamAButton2.setVisibility(View.GONE);
        teamBButton1.setText(playerNames[(teamPossession^1)*2] + "\nmade an unforced error");
        teamBButton2.setText(playerNames[(teamPossession^1)*2+1] + "\n made an unforced error");
        state = 4;
    }

    public void askForServer(){
        instructionTextView.setText(R.string.who_serve_tool_tip);
        teamAButton1.setVisibility(View.VISIBLE);
        teamAButton2.setVisibility(View.VISIBLE);
        teamBButton1.setVisibility(View.VISIBLE);
        teamBButton2.setVisibility(View.VISIBLE);
        teamAButton1.setText(playerNames[0]);
        teamAButton2.setText(playerNames[1]);
        teamBButton1.setText(playerNames[2]);
        teamBButton2.setText(playerNames[3]);
        state = 0;
    }

    public void changePossessionState(){
        teamPossession ^= 1;
        teamAButton1.setVisibility(View.VISIBLE);
        teamAButton2.setVisibility(View.VISIBLE);
        teamBButton1.setVisibility(View.VISIBLE);
        teamBButton2.setVisibility(View.VISIBLE);
        teamAButton1.setText(playerNames[teamPossession*2]);
        teamBButton1.setText(playerNames[teamPossession*2+1]);
        teamAButton2.setText("Change of possession\nClean off the net");
        teamBButton2.setText("Ground/Rim");


    }

    public void setupStateTouch(int team) {
        instructionTextView.setText("Keep track of who touches the ball");
        teamAButton1.setVisibility(View.VISIBLE);
        teamAButton2.setVisibility(View.VISIBLE);
        teamBButton1.setVisibility(View.VISIBLE);
        teamBButton2.setVisibility(View.VISIBLE);
        teamAButton1.setText(playerNames[team*2]);
        teamBButton1.setText(playerNames[team*2+1]);
        teamAButton2.setText("Change of possession\nClean off the net");
        teamBButton2.setText("Ground/Rim");

        state = 3;

    }


    public void setupStateServing(){
        //teamPossession is the serving team!
        System.out.println("HI I MADE IT");
        String instructionString=" is now serving their first attempt";
        String appendServe = "S";
        if(serveAttempt == 2){
            appendServe = "s";
            instructionString = " is now serving their seccond attempt";
        }

        if(firstServers[teamPossession] == -1){
            askForServer();
            return;
        }
        rallyBuilder.append(appendServe);
        rallyBuilder.append(currentServer());

        instructionTextView.setText(playerNames[currentServer()] + instructionString);
        teamAButton1.setText(R.string.clean);
        teamBButton1.setText(R.string.fault);
        teamAButton2.setVisibility(View.GONE);
        teamBButton2.setVisibility(View.GONE);
        state = 2;

    }
    public void isServing(int server){
        for (int i=0; i<4; i++){
            whoServed[i] = false;
        }
        whoServed[server]=true;
    }

    public int currentServer(){
        for(int i=0; i<4; i++){
            if(whoServed[i]){
                return i;
            }
        }
        return -1;
    }




}
