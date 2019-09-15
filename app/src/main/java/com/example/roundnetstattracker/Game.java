package com.example.roundnetstattracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class Game implements Parcelable{

    Team teamA;
    Team teamB;
    private String id;
    private int serve1;
    private int serve2;
    private List<String> rallies;

    public Game(String _id){
        teamA = new Team("teamA");
        teamB = new Team("teamB");
        id = _id;
        serve1=-1;
        serve2=-1;

    }

    private Game(Parcel parcel) {
        teamA = parcel.readParcelable(Team.class.getClassLoader());
        teamB = parcel.readParcelable(Team.class.getClassLoader());
        id = parcel.readString();
        serve1 = parcel.readInt();
        serve2 = parcel.readInt();
        //TODO: parcel read and write List

    }

    public void setServe1(int i){ this.serve1 = i; }
    public int getServe1(){ return this.serve1; }
    public void setServe2(int i){ this.serve2 = i; }
    public int getServe2(){ return this.serve2; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(teamA,0);
        parcel.writeParcelable(teamB,0);
        parcel.writeString(id);
        parcel.writeInt(serve1);
        parcel.writeInt(serve2);
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
