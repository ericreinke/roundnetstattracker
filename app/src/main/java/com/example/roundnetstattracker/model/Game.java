package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.UUID;

@Entity(tableName = "games")
public class Game implements Parcelable{

    @Ignore
    public TeamGameProfile teamGameProfileA;

    @Ignore
    public TeamGameProfile teamGameProfileB;

    @Ignore
    public Team teamA;

    @Ignore
    public Team teamB;

    @Ignore
    public GameManager gm;

    @ColumnInfo(name = "team_a_profile_id")
    public  String teamAProfileId; // is a string uuid

    @ColumnInfo(name = "team_b_profile_id")
    public  String teamBProfileId; // is a string uuid

    @PrimaryKey
    @NonNull
    public  String uid = UUID.randomUUID().toString();

    @Ignore
    private ArrayList<String> rallies = new ArrayList<>();

    public Game(){
        System.out.println("creating game");
        teamGameProfileA = new TeamGameProfile("replace me");
        teamGameProfileB = new TeamGameProfile("repalce me");
        teamAProfileId = teamGameProfileA.uid;
        teamBProfileId = teamGameProfileB.uid;
        gm = new GameManager();

    }

    private Game(Parcel parcel) {
        teamGameProfileA = parcel.readParcelable(TeamGameProfile.class.getClassLoader());
        teamGameProfileB = parcel.readParcelable(TeamGameProfile.class.getClassLoader());
        gm = parcel.readParcelable(GameManager.class.getClassLoader());
        rallies = parcel.createStringArrayList();


    }
    public void addRally(String rally){
        rallies.add(rally);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(teamGameProfileA,0);
        parcel.writeParcelable(teamGameProfileB,0);
        parcel.writeParcelable(gm, 0);
    }

    public static final Parcelable.Creator<Game> CREATOR=
            new Creator<Game>() {
                @Override
                public Game createFromParcel(Parcel parcel) {
                    return new Game(parcel);
                }
                @Override
                public Game[] newArray(int size) {
                    return new Game[size];
                }
            };
}
