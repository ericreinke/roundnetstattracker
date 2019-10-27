package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GameManager implements Parcelable {

    int server;
    int receiver;
    int servingTeam;
    int receivingTeam;
    int nextServer;
    int [] playerAcross =  new int [4];

    public GameManager(Parcel parcel) {
        server = parcel.readInt();
        receiver = parcel.readInt();
        servingTeam = parcel.readInt();
        receivingTeam = parcel.readInt();
        nextServer = parcel.readInt();
        playerAcross = parcel.createIntArray();
    }

    public GameManager(int server, int receiver, int serverTeammate, int receiverTeammate){
        this.server = server;
        this.receiver = receiver;
        this.servingTeam = server/2;
        this.receivingTeam = receiver/2;
        this.nextServer = -1; //needs to be updated by user.
        playerAcross[server] = receiver;
        playerAcross[receiver] = server;
        playerAcross[serverTeammate] = receiverTeammate;
        playerAcross[receiverTeammate] = serverTeammate;
    }

    public void setNextServer(int nextServer){this.nextServer = nextServer;}

    public void updateGameManager(String breakOrNot){
        assert(nextServer!=-1);
        if(breakOrNot.equals("break")){
            receiver =  changeTeammate(receiver);
            playerAcross[0] = changeTeammate(playerAcross[0]);
            playerAcross[1] = changeTeammate(playerAcross[1]);
            playerAcross[2] = changeTeammate(playerAcross[2]);
            playerAcross[3] = changeTeammate(playerAcross[4]);
        }
        else if(!breakOrNot.equals("break")){
            int tempServer = server;
            server = nextServer;
            receiver = playerAcross[server];
            servingTeam ^= 1;
            receivingTeam ^= 1;
            nextServer = changeTeammate(tempServer);
        }
    }

    // 0->1, 1->0, 2->3, 3->2
    public int changeTeammate(int player){return (player/2) * 2 + player%2^1;}


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
        parcel.writeIntArray(playerAcross);
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
