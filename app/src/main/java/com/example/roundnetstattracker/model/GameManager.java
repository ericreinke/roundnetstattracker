package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GameManager implements Parcelable {

    int server;
    int receiver;
    int servingTeam;
    int receivingTeam;
    int nextServer;
    int [] scores;
    int [] playerAcross;
    boolean populated;
    private ArrayList<String> rallies = new ArrayList<>();

    public GameManager(Parcel parcel) {
        server = parcel.readInt();
        receiver = parcel.readInt();
        servingTeam = parcel.readInt();
        receivingTeam = parcel.readInt();
        nextServer = parcel.readInt();
        scores = parcel.createIntArray();
        playerAcross = parcel.createIntArray();
        rallies = parcel.createStringArrayList();
    }

    public GameManager(){//gm is unusable until populated
        populated=false;
    }

    public int getServer() {
        return server;
    }

    public int getReceiver() {
        return receiver;
    }

    public int getServingTeam() {
        return servingTeam;
    }

    public int getAScore() { return scores[0]; }

    public int getBScore() { return scores[1]; }

    public int getReceivingTeam() {
        return receivingTeam;
    }

    public int getNextServer() {
        return nextServer;
    }

    public void addRally(String rally){
        rallies.add(rally);
    }

    public void populateGameManager(int server, int serverTeammate, int receiver, int receiverTeammate){
        playerAcross = new int[4];
        scores = new int[2];
        this.server = server;
        this.receiver = receiver;
        this.servingTeam = server/2;
        this.receivingTeam = receiver/2;
        this.nextServer = -1; //needs to be updated in a separate call by user.
        playerAcross[server] = receiver;
        playerAcross[receiver] = server;
        playerAcross[serverTeammate] = receiverTeammate;
        playerAcross[receiverTeammate] = serverTeammate;
        scores[0] = 0;
        scores[1] = 0;

        populated=true;
    }

    public void setNextServer(int nextServer){this.nextServer = nextServer;}

    public void updateGameManager(String breakOrNot){
        assert(nextServer!=-1);
        if(breakOrNot.equals("break")){
            scores[servingTeam]++;

            receiver =  getTeammate(receiver);
            playerAcross[0] = getTeammate(playerAcross[0]);
            playerAcross[1] = getTeammate(playerAcross[1]);
            playerAcross[2] = getTeammate(playerAcross[2]);
            playerAcross[3] = getTeammate(playerAcross[3]);
        }
        else if(!breakOrNot.equals("break")){
            scores[receivingTeam]++; // Note score is updated first

            int tempServer = server;
            server = nextServer;
            receiver = playerAcross[server];
            servingTeam ^= 1;
            receivingTeam ^= 1;
            nextServer = getTeammate(tempServer);
        }
    }

    /*
     * Return true if a team has touched 3 times (they canNOT have another touch)
     */
    public boolean checkMustChangePossesion(String rally){
        int currentTeam = Character.getNumericValue(rally.charAt(rally.length()-1))/2;
        for(int i = 2; i < 4; i++){
            if(Character.getNumericValue(rally.charAt(rally.length()-i))/2 != currentTeam){
                return false;
            }
        }
        return true;
    }

    // 0->1, 1->0, 2->3, 3->2
    public int getTeammate(int player){return (player/2) * 2 + player%2^1;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(server);
        parcel.writeInt(receiver);
        parcel.writeInt(servingTeam);
        parcel.writeInt(receivingTeam);
        parcel.writeInt(nextServer);
        parcel.writeIntArray(scores);
        parcel.writeIntArray(playerAcross);
        parcel.writeStringList(rallies);
    }

    public static final Parcelable.Creator<GameManager> CREATOR=
            new Creator<GameManager>() {
                @Override
                public GameManager createFromParcel(Parcel parcel) {
                    return new GameManager(parcel);
                }
                @Override
                public GameManager[] newArray(int size) {
                    return new GameManager[size];
                }
            };
}