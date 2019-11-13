package com.example.roundnetstattracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.roundnetstattracker.enums.States;
import com.example.roundnetstattracker.model.Game;
import com.example.roundnetstattracker.room.AppDatabase;

public class RecordGameActivity extends AppCompatActivity {

    private final String SERVING = States.SERVING.getState();
    private final String RECEIVING = States.RECEIVING.getState();
    private final String ERROR = States.ERROR.getState();

    TextView teamATextView;
    TextView teamBTextView;
    TextView instructionTextView;
    TextView teamAScoreTextView;
    TextView teamBScoreTextView;
    TextView rallyTextView;
    Button teamAButton1;
    Button teamBButton1;
    Button teamAButton2;
    Button teamBButton2;
    Button errorButton;
    Button serviceFaultButton;
    Button scoreAButton;
    Button scoreBButton;
    Button saveButton;
    Button [] allButtons;
    Game myGame = null;
    StringBuilder currentRally;
    String state = SERVING ;
    int[] empty = {};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamATextView = findViewById(R.id.teamANameTextView);
        teamBTextView = findViewById(R.id.teamBNameTextView);
        instructionTextView = findViewById(R.id.instructionTextView);
        teamAScoreTextView = findViewById(R.id.teamAScoreTextView);
        teamBScoreTextView = findViewById(R.id.teamBScoreTextView);
        teamAButton1 = findViewById(R.id.teamAButton1);
        teamBButton1 = findViewById(R.id.teamBButton1);
        teamAButton2 = findViewById(R.id.teamAButton2);
        teamBButton2 = findViewById(R.id.teamBButton2);
        errorButton = findViewById(R.id.errorButton);
        serviceFaultButton = findViewById(R.id.serviceFaultButton);
        scoreAButton = findViewById(R.id.plusScoreAButton);
        scoreBButton = findViewById(R.id.plusScoreBButton);
        allButtons = new Button [] {teamAButton1, teamAButton2, teamBButton1, teamBButton2,
                errorButton, serviceFaultButton, scoreAButton, scoreBButton};
        saveButton = findViewById(R.id.saveButton);
        rallyTextView = findViewById(R.id.rallyDebugTextView);

        myGame = getIntent().getExtras().getParcelable("GAME_OBJECT");
        assert(myGame!=null);

        myGame.gm.populateGameManager(0, 1, 2, 3);
        myGame.gm.setNextServer(2);
        disableAllButtonsBut(myGame.gm.getServer());

        teamATextView.setText(myGame.teamGameProfileA.getName());
        teamBTextView.setText(myGame.teamGameProfileB.getName());
        teamAScoreTextView.setText(Integer.toString(myGame.gm.getAScore()));
        teamBScoreTextView.setText(Integer.toString(myGame.gm.getBScore()));
        teamAButton1.setText(myGame.teamGameProfileA.playerGameProfile1.getName());
        teamAButton2.setText(myGame.teamGameProfileA.playerGameProfile2.getName());
        teamBButton1.setText(myGame.teamGameProfileB.playerGameProfile1.getName());
        teamBButton2.setText(myGame.teamGameProfileB.playerGameProfile2.getName());
        instructionTextView.setText("");

        currentRally = new StringBuilder();
        currentRally.append("S");
        state = "serving";


    }

    public void button1AOnClick(View view){
        currentRally.append("0");
        enableAllButtonsBut(0);

        if(state.equals(SERVING)){ setAndUpdateState(RECEIVING); }
        else if(state.equals(ERROR)){
            myGame.gm.updateGameManager(getBreakString(0));
            rallyOver();
        }

        rallyTextView.setText(currentRally.toString());
    }
    public void button2AOnClick(View view){
        currentRally.append("1");
        enableAllButtonsBut(1);

        if(state.equals(SERVING)){ setAndUpdateState(RECEIVING); }
        else if(state.equals(ERROR)){
            myGame.gm.updateGameManager(getBreakString(1));
            rallyOver();
        }

        rallyTextView.setText(currentRally.toString());
    }
    public void button1BOnClick(View view){
        currentRally.append("2");
        enableAllButtonsBut(2);

        if(state.equals(SERVING)){ setAndUpdateState(RECEIVING); }
        else if(state.equals(ERROR)){
            myGame.gm.updateGameManager(getBreakString(2));
            rallyOver();
        }

        rallyTextView.setText(currentRally.toString());
    }
    public void button2BOnClick(View view){
        currentRally.append("3");
        enableAllButtonsBut(3);

        if(state.equals(SERVING)){ setAndUpdateState(RECEIVING); }
        else if(state.equals(ERROR)){
            myGame.gm.updateGameManager(getBreakString(3));
            rallyOver();
        }

        rallyTextView.setText(currentRally.toString());
    }
    public void errorOnClick(View view){
        currentRally.append("E");
        state = ERROR;
        disableAllButtonsBut(0,1,2,3);

        rallyTextView.setText(currentRally.toString());
    }
    public void serviceFaultOnClick(View view){
        if(currentRally.indexOf("F") < 0){
            currentRally.append("F");
            currentRally.append("s");
            setAndUpdateState(SERVING);
        }
        else if(currentRally.indexOf("f") < 0){
            currentRally.append("f");
            myGame.gm.updateGameManager("not_a_break");
            rallyOver();
        }
        else{
            // we already have "F" and "f" and somehow we have another fault.  something's up
        }
        rallyTextView.setText(currentRally.toString());
    }

    public void saveGameOnClick(View view){
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());

        db.playerProfileDao().insertAll(myGame.teamGameProfileA.playerGameProfile1);
        db.playerProfileDao().insertAll(myGame.teamGameProfileA.playerGameProfile2);
        db.playerProfileDao().insertAll(myGame.teamGameProfileB.playerGameProfile1);
        db.playerProfileDao().insertAll(myGame.teamGameProfileB.playerGameProfile2);
    }


    /* Note about the following 2 methods:  getBreakString(int) takes the int of a player who is NOT
     * scoring.  So in this case we are just passing the opponent (2 is not on A, 0 is not on B)
     * Its sketchy and getBreakString() should probably be renamed.
     *
     * Sorry
     */
    public void plusScoreAOnClick(View view){
        myGame.gm.updateGameManager(getBreakString(2));
        rallyOver();
    }
    public void plusScoreBOnClick(View view){
        myGame.gm.updateGameManager(getBreakString(0));
        rallyOver();
    }

    public void rallyOver(){
        teamAScoreTextView.setText(Integer.toString(myGame.gm.getAScore()));
        teamBScoreTextView.setText(Integer.toString(myGame.gm.getBScore()));
        instructionTextView.setText("");
        myGame.addRally(currentRally.toString());
        currentRally.setLength(0);
        currentRally.append("S");
        setAndUpdateState(SERVING);
    }

    public void enableAllButtonsBut(int ... buttons){
        for(int i = 0; i < allButtons.length; i++){
            allButtons[i].setEnabled(true);
        }
        for(int i = 0; i < buttons.length; i++){
            allButtons[buttons[i]].setEnabled(false);
        }
    }

    public void disableAllButtonsBut(int ... buttons){
        for(int i = 0; i < allButtons.length; i++){
            allButtons[i].setEnabled(false);
        }
        for(int i = 0; i < buttons.length; i++){
            allButtons[buttons[i]].setEnabled(true);
        }
    }

    public void setAndUpdateState(String newState){
        state = newState;
        System.out.println(state);

        if(state.equals(SERVING)){
            disableAllButtonsBut(myGame.gm.getServer());
        }
        else if(state.equals(RECEIVING)){
            disableAllButtonsBut(myGame.gm.getReceiver(), 5, myGame.gm.getServingTeam()+6);
        }
        else if(state.equals(ERROR)){
            disableAllButtonsBut(0, 1, 2, 3);
            instructionTextView.setText("Please select who made the error");

        }
    }

    public String getBreakString(int player){ // take in the player who DIDN'T SCORE!!!
        if(myGame.gm.getServingTeam() == player/2){
            return "not_a_break";
        }
        else {
            return "break";
        }
    }



    public void saveGame(View view){
        //======================================================
        // We want to use a room data base for this pleaseeeeee
        //======================================================
//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting();
//        Gson gson = builder.create();
//        String myGameJson = gson.toJson(myGame);
//        System.out.println(myGameJson);
//
//        String fileName = "games.json";
//        FileOutputStream fos = null;
//
//        File dir = getFilesDir();
//        File checkFile = new File(dir,fileName);
//
//        if(checkFile.exists()){
//            myGameJson = ", " + myGameJson;
//        }
//
//
//        try {
//            fos = openFileOutput(fileName, MODE_APPEND);
//            fos.write(myGameJson.getBytes());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//            if(fos != null){
//                try{
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        Intent intent = new Intent(RecordGameActivity.this, GameDetails.class);
//        intent.putExtra("GAME_OBJECT", myGame);
//        //We need to either pass the Game object or the ID here.  Game object is probbably eaiser
//        startActivity(intent);
    }




}
