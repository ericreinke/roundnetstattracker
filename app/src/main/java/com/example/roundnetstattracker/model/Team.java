package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable {

    public Player player1;
    public Player player2;
    private String name;
    private int score;


    public Team(String teamName){
        player1 = new Player("player1");
        player2 = new Player("player2");
        String name = teamName;
        score = 0;
    }

    public Team(Parcel parcel) {
        player1 = parcel.readParcelable(Player.class.getClassLoader());
        player2 = parcel.readParcelable(Player.class.getClassLoader());
        name = parcel.readString();
        score = parcel.readInt();
    }

    public void addScore(int diff){
        this.score+=diff;
    }

    public int getScore(){
        return this.score;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(player1,0);
        parcel.writeParcelable(player2,0);
        parcel.writeString(name);
        parcel.writeInt(score);
    }

    public static final Parcelable.Creator<Team> CREATOR =
            new Creator<Team>() {
                @Override
                public Team createFromParcel(Parcel parcel) {
                    return new Team(parcel);
                }

                @Override
                public Team[] newArray(int i) {
                    return new Team[i];
                }
            };
}
