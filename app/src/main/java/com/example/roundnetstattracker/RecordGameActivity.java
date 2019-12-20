package com.example.roundnetstattracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.roundnetstattracker.enums.States;
import com.example.roundnetstattracker.model.Game;
import com.example.roundnetstattracker.model.PlayerGameProfile;
import com.example.roundnetstattracker.model.TeamGameProfile;
import com.example.roundnetstattracker.room.AppDatabase;

public class RecordGameActivity extends AppCompatActivity {

    private final Logger log = Logger.getLogger(RecordGameActivity.class.getName());
    private final String SERVING = States.SERVING.getState();
    private final String RECEIVING = States.RECEIVING.getState();
    private final String ERROR = States.ERROR.getState();
    private final String SETUP = States.SETUP.getState();

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
    Button hinderButton;
    Button [] allButtons;
    Game myGame;
    StringBuilder currentRally;
    String state = SERVING;
    List<Integer> setupList = new ArrayList<>();

    TeamGameProfile teamAProfile;
    TeamGameProfile teamBProfile;
    PlayerGameProfile playerA1Profile;
    PlayerGameProfile playerA2Profile;
    PlayerGameProfile playerB1Profile;
    PlayerGameProfile playerB2Profile;
    PlayerGameProfile [] allPGP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        log.setLevel(Level.INFO);

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
        hinderButton = findViewById(R.id.hinderButton);
        allButtons = new Button [] {teamAButton1, teamAButton2, teamBButton1, teamBButton2,
                errorButton, serviceFaultButton, scoreAButton, scoreBButton,hinderButton};
        saveButton = findViewById(R.id.saveButton);
        rallyTextView = findViewById(R.id.rallyDebugTextView);

        myGame = getIntent().getExtras().getParcelable("GAME_OBJECT");
        teamAProfile = getIntent().getExtras().getParcelable("TEAM_A_PROFILE");
        teamBProfile = getIntent().getExtras().getParcelable("TEAM_B_PROFILE");
        playerA1Profile = getIntent().getExtras().getParcelable("PLAYER_A_1_PROFILE");
        playerA2Profile = getIntent().getExtras().getParcelable("PLAYER_A_2_PROFILE");
        playerB1Profile = getIntent().getExtras().getParcelable("PLAYER_B_1_PROFILE");
        playerB2Profile = getIntent().getExtras().getParcelable("PLAYER_B_2_PROFILE");
        allPGP = new PlayerGameProfile[] {playerA1Profile, playerA2Profile, playerB1Profile, playerB2Profile};

        assert(myGame!=null);

        //myGame.gm.populateGameManager(0, 1, 2, 3);
        //myGame.gm.setNextServer(2)

        disableAllButtonsBut(0, 1, 2, 3);

        teamATextView.setText(teamAProfile.teamName);
        teamBTextView.setText(teamBProfile.teamName);
        teamAScoreTextView.setText(Integer.toString(myGame.gm.getAScore()));
        teamBScoreTextView.setText(Integer.toString(myGame.gm.getBScore()));
        teamAButton1.setText(playerA1Profile.playerName);
        teamAButton2.setText(playerA2Profile.playerName);
        teamBButton1.setText(playerB1Profile.playerName);
        teamBButton2.setText(playerB2Profile.playerName);

        setAndUpdateState(SETUP);
        instructionTextView.setText("Tap in order: First server, First receiver, Next server");

        currentRally = new StringBuilder();
        currentRally.append("S");
    }

    public void button1AOnClick(View view){
        onPlayerButtonClick(0);
    }

    public void button2AOnClick(View view){
        onPlayerButtonClick(1);
    }

    public void button1BOnClick(View view){
        onPlayerButtonClick(2);
    }

    public void button2BOnClick(View view){
        onPlayerButtonClick(3);
    }

    public void errorOnClick(View view){
        currentRally.append("E");
        state = ERROR;
        disableAllButtonsBut(0,1,2,3);
        instructionTextView.setText("Tap on the player who made the error");
        rallyTextView.setText(currentRally.toString());
    }
    public void serviceFaultOnClick(View view){
        if(currentRally.indexOf("F") < 0){
            currentRally.append("F");
            currentRally.append("s");
            setAndUpdateState(SERVING);
        }
        else{// if(currentRally.indexOf("f") < 0){
            currentRally.append("f");
            currentRally.append("D");
            currentRally.append(myGame.gm.getReceivingTeam());
            rallyOver("not_a_break");
        }
        rallyTextView.setText(currentRally.toString());
    }

    private void onPlayerButtonClick(int player){
        if(state.equals(SETUP)){
            System.out.println("adding setup: " + player);
            setupList.add(player);
            if(setupList.size() == 3){
                myGame.gm.populateGameManager(setupList.get(0), setupList.get(1), setupList.get(2));
                setAndUpdateState(SERVING);
            }
            return;
        }
        hinderButton.setText("Hinder\n(Cancel current rally)");
        currentRally.append(player);
        enableAllButtonsBut(player, myGame.gm.checkMustChangePossesion(currentRally.toString()) ?
                myGame.gm.getTeammate(player) : player );

        if(state.equals(SERVING)){
            setAndUpdateState(RECEIVING);
        }
        else if(state.equals(ERROR)){
            // We added the player already, at the start of this button method
            currentRally.append("D");
            currentRally.append((player/2)^1);
            rallyOver(getBreakString(player));
        }
        rallyTextView.setText(currentRally.toString());
    }

    public void hinderOnClick(View view){
        if(state.equals(SERVING)){
            if(myGame.gm.getAScore() == 0 && myGame.gm.getBScore() == 0){
                Toast toast = new Toast(getApplicationContext());
                toast.makeText(this.getApplicationContext(),"No more rallies to undo",Toast.LENGTH_LONG).show();
                return;
            }
            else{
                myGame.gm.reverseGameManager(); // This removes the last rally and reverses gm
                instructionTextView.setText("Removed previous rally");
            }
        }
        else{
            instructionTextView.setText("Cancelled current rally");
        }
        currentRally.setLength(0);
        currentRally.append("S");
        setAndUpdateState(SERVING);
        teamAScoreTextView.setText(Integer.toString(myGame.gm.getAScore()));
        teamBScoreTextView.setText(Integer.toString(myGame.gm.getBScore()));


    }

    public void saveGame(View view){
        // These two lines will save a couple db queries in GamesActivity (to display score)
        teamAProfile.teamScore = myGame.gm.getAScore();
        teamBProfile.teamScore = myGame.gm.getBScore();

        Thread t = new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.playerProfileDao().insertAll(playerA1Profile, playerA2Profile, playerB1Profile, playerB2Profile);
                db.teamProfileDAO().insertAll(teamAProfile, teamBProfile);
                db.gameDao().insertAll(myGame);
            }
        });
        t.start();
        try{
            t.join();
        }catch(InterruptedException e){
            System.out.println("Thread was interrupted.  I have no idea what this means");
        }
        finish();
    }


    /*
     * Note about the following 2 methods:  getBreakString(int) takes the int of a player who is NOT
     * scoring.  So in this case we are just passing the opponent (2 is not on A, 0 is not on B)
     * Its sketchy and getBreakString() should probably be renamed.
     *
     * Sorry
     */
    public void plusScoreAOnClick(View view){
        currentRally.append("D");
        currentRally.append("0");
        rallyOver(getBreakString(2));
    }
    public void plusScoreBOnClick(View view){
        currentRally.append("D");
        currentRally.append("1");
        rallyOver(getBreakString(0));
    }

    public void rallyOver(String breakString){
        int server = myGame.gm.getServer();

        //Analyze total(First/Second)Serve
        if(currentRally.indexOf("S") >= 0){
            allPGP[server].totalFirstServe++;
            log.info("Increasing totalFirstServe for player " + server);
        }
        if(currentRally.indexOf("s") >= 0){
            allPGP[server].totalSecondServe++;
            log.info("Increasing totalSecondServe for player " + server);
        }

        //Analyze (first/second)ServeFault
        if(currentRally.indexOf("F") >= 0){
            allPGP[server].firstServeFault++;
            log.info("Increasing firstServeFault for player "+ server);
        }
        if(currentRally.indexOf("f") >= 0){
            allPGP[server].secondServeFault++;
            log.info("Increasing secondServeFault for "+ server);
        }

        //Analyze Ace/Aced
        if(rallyWasAce(currentRally.toString())){
            allPGP[server].ace++;
            allPGP[myGame.gm.getReceiver()].aced++;
            log.info("Increasing ace for player "+ server);
            log.info("Increasing aced for player "+ myGame.gm.getReceiver());
        }

        // Analyze Error
        int errorPlayerIndex = currentRally.indexOf("E")+1;
        if(errorPlayerIndex >= 1){
            allPGP[Character.getNumericValue(currentRally.charAt(errorPlayerIndex))].error++;
            log.info("Increasing error for player "+
                    Character.getNumericValue(currentRally.charAt(errorPlayerIndex)));
        }

        // Analyze Put Away Success
        int putAwayResult = putAwaySuccess(currentRally.toString());
        if(putAwayResult >=0){
            allPGP[putAwayResult].putAwaySuccess++;
            log.info("Increasing putAwaySuccess for player "+putAwayResult);
        }

        // Analyze Put Away Failures
        List<Integer> lof = putAwayFailures(currentRally.toString());
        for(int i = 0; i < lof.size(); i++){
            allPGP[lof.get(i)].putAwayFailure++;
        }

        // Analyze Defensive gets
        List<Integer> lodg = defensiveGets(currentRally.toString());
        for(int i = 0; i < lodg.size(); i++){
            allPGP[lodg.get(i)].defensiveGet++;
        }


        myGame.gm.updateGameManager(breakString);
        setAndUpdateState(SERVING);
        teamAScoreTextView.setText(Integer.toString(myGame.gm.getAScore()));
        teamBScoreTextView.setText(Integer.toString(myGame.gm.getBScore()));
        instructionTextView.setText("");
        myGame.gm.addRally(currentRally.toString());
        log.info("Added rally: "+ currentRally);
        currentRally.setLength(0);
        currentRally.append("S");
        hinderButton.setText("Undo last rally");
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
        log.info("Changing state from " + state + " to " + newState);
        state = newState;

        if(state.equals(SERVING)){
            disableAllButtonsBut(myGame.gm.getServer());
        }
        else if(state.equals(SETUP)){
            disableAllButtonsBut(0,1,2,3);
        }
        else if(state.equals(RECEIVING)){
            disableAllButtonsBut(myGame.gm.getReceiver(), 5, myGame.gm.getServingTeam()+6,8);
        }
        else if(state.equals(ERROR)){
            disableAllButtonsBut(0, 1, 2, 3);
            instructionTextView.setText("Please select who made the error");
        }
    }

    /*
     * Please come up with a more clever way.  At LEAST use an enum ffs.
     */
    public String getBreakString(int player){ // take in the player who DIDN'T SCORE!!!
        if(myGame.gm.getServingTeam() == player/2){
            return "not_a_break";
        }
        else {
            return "break";
        }
    }

    public boolean rallyWasAce(String rally){
        int serveIndex = rally.toLowerCase().lastIndexOf('s');
        int servingTeam = Character.getNumericValue(rally.charAt(serveIndex+1))/2;
        int receivingTeam = servingTeam^1;

        //serving team has to score for it to be an ace (note this covers (S0Fs0fD1)
        if(scoringTeam(rally) != servingTeam){ return false; }

        //If the touch after serve is not opponent then it's an ace
        if(Character.getNumericValue(rally.charAt(serveIndex+2))/2 == receivingTeam &&
                Character.getNumericValue(rally.charAt(serveIndex+3)) <= 3){
            return false;
        }
        return true;
    }

    public int putAwaySuccess(String rally){
        // If the rally was an ace, there can be no put away
        if(rallyWasAce(rally)){ return -1; }

        // If the rally was a double fault, there can be no put away
        if(rally.indexOf("f") >= 0){ return -1; }

        // Otherwise there MUST be a put away (errors still -> put away)
        // We want to start at the 2nd LAST(or 3rd last, but just not the absolute last) character
        // and keep going backwards to figure out who put it away (last to touch)
        for(int i=rally.length()-2 ; i >=0; i--){
            int currChar = Character.getNumericValue(rally.charAt(i));
            if(currChar/2 == scoringTeam(rally)){
                return currChar;
            }
        }

        return -1; // This should never happen
    }

    /*
     * This method is to analyze a rally and determine the number of put away failures and defensive gets
     * Note that putAwayFailure == defensiveGet in every rally
     */
    private List<Integer> putAwayFailuresAndDefensiveGets(String rally, int offset){
        List<Integer> theList = new ArrayList<>();
        if(rallyWasAce(rally)){ return theList; }
        if(rally.indexOf("f") >= 0){ return theList; }

        int startOfPossessionIndex = rally.toLowerCase().lastIndexOf("s")+2;

        while(true) {
            int teamPossession = Character.getNumericValue(rally.charAt(startOfPossessionIndex)) / 2;
            int opposingTeam = teamPossession ^ 1;
            int lastLastTeamTouchIndex = Math.max(rally.lastIndexOf((char)(teamPossession * 2 +48)),
                    rally.lastIndexOf((char)(teamPossession * 2 + 49), startOfPossessionIndex));
            int lastLastOpponentTeamTouch = Math.max(rally.lastIndexOf((char)(opposingTeam * 2+48)),
                    rally.lastIndexOf((char)(opposingTeam * 2 + 49), startOfPossessionIndex));
            int lastTeamTouchIndex = lastPossessionTouchIndex(startOfPossessionIndex, rally);

            if (lastTeamTouchIndex < 0) break; // No more change of possessions;
            if (lastLastTeamTouchIndex > lastTeamTouchIndex ||
                    (lastLastOpponentTeamTouch > lastTeamTouchIndex && scoringTeam(rally) == opposingTeam)) {
                theList.add(Character.getNumericValue(rally.charAt(lastTeamTouchIndex+offset)));
                log.info("Increasing putAwayFailure for player " + Character.getNumericValue(rally.charAt(lastTeamTouchIndex+offset)));
            }
            startOfPossessionIndex = lastTeamTouchIndex + 1;
        }
        return theList;
    }

    private List<Integer> putAwayFailures(String rally){
        return putAwayFailuresAndDefensiveGets(rally, 0);
    }

    private List<Integer> defensiveGets(String rally){
        return putAwayFailuresAndDefensiveGets(rally, 1);
    }

    private int whoError(String rally){
        if(rally.indexOf("E")<0) return -1;
        return Character.getNumericValue(rally.charAt(rally.indexOf("E")+1));
    }

    private int scoringTeam(String rally){
        return Character.getNumericValue(rally.charAt(rally.indexOf("D")+1));
    }

    private int lastPossessionTouchIndex(int startSearch, String rally){
        for(int i=startSearch; i<rally.length(); i++){
            int currTeamValue = Character.getNumericValue(rally.charAt(i))/2;
            int nextTeamValue = Character.getNumericValue(rally.charAt(i+1))/2;

            if(nextTeamValue != 0 && nextTeamValue != 1) return -1;
            if(currTeamValue != 0 && currTeamValue != 1) return -1;

            if(currTeamValue != nextTeamValue) return i;
        }
        return -1;
    }
}
