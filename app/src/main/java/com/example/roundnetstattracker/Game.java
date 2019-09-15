package com.example.roundnetstattracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class Game implements Parcelable{

    Team teamA;
    Team teamB;
    private String id;
    private ArrayList<String> rallies = new ArrayList<>();

    public Game(String _id){
        teamA = new Team("teamA");
        teamB = new Team("teamB");
        id = _id;


    }

    private Game(Parcel parcel) {
        teamA = parcel.readParcelable(Team.class.getClassLoader());
        teamB = parcel.readParcelable(Team.class.getClassLoader());
        id = parcel.readString();
        //TODO: parcel read and write List

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
        parcel.writeParcelable(teamA,0);
        parcel.writeParcelable(teamB,0);
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
