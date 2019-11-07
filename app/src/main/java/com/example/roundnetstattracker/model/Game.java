package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Game implements Parcelable{

    public TeamGameProfile teamGameProfileA;
    public TeamGameProfile teamGameProfileB;
    public GameManager gm;
    private String id;
    private ArrayList<String> rallies = new ArrayList<>();

    public Game(String _id){
        teamGameProfileA = new TeamGameProfile("teamGameProfileA");
        teamGameProfileB = new TeamGameProfile("teamGameProfileB");
        id = _id;
        gm = new GameManager();
    }

    private Game(Parcel parcel) {
        teamGameProfileA = parcel.readParcelable(TeamGameProfile.class.getClassLoader());
        teamGameProfileB = parcel.readParcelable(TeamGameProfile.class.getClassLoader());
        gm = parcel.readParcelable(GameManager.class.getClassLoader());
        id = parcel.readString();
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
        parcel.writeString(id);
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
