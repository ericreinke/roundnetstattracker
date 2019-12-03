package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "players")
public class Player implements Parcelable {

    @PrimaryKey
    @NonNull
    public String uid;

    public String name;

    public Player(String uid, String name){
        this.uid = uid;
        this.name = name;
    }

    public Player(Parcel parcel){
        this.uid = parcel.readString();
        this.name = parcel.readString();
    }

    public Player(boolean anything){
        name = "player" + uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
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
