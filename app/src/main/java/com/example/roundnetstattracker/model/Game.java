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

    @PrimaryKey
    @NonNull
    public String uid;

    public String teamAId;

    public String teamBId;

    public String teamAProfileId;

    public String teamBProfileId;

    @Ignore
    public GameManager gm;

    public Game(){}

    public Game(String uid, String teamAId, String teamBId, String teamAProfileId, String teamBProfileId, GameManager gm){
        this.uid = uid;
        this.teamAId = teamAId;
        this.teamBId = teamBId;
        this.teamAProfileId = teamAProfileId;
        this.teamBProfileId = teamBProfileId;
        this.gm = gm;
    }

    private Game(Parcel parcel) {
        uid = parcel.readString();
        teamAId = parcel.readString();
        teamBId = parcel.readString();
        teamAProfileId = parcel.readString();
        teamBProfileId = parcel.readString();
        gm = parcel.readParcelable(GameManager.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(teamAId);
        parcel.writeString(teamBId);
        parcel.writeString(teamAProfileId);
        parcel.writeString(teamBProfileId);
        parcel.writeParcelable(gm,0);
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
