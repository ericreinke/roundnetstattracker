package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    private String name;

    public Player(String playerName){
        name=playerName;
    }

    public Player(Parcel parcel) {
        name = parcel.readString();
    }

    public void setName(String name){
        this.name= name;
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
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Player> CREATOR =
            new Creator<Player>() {
                @Override
                public Player createFromParcel(Parcel parcel) {
                    return new Player(parcel);
                }

                @Override
                public Player[] newArray(int i) {
                    return new Player[i];
                }
            };
}
