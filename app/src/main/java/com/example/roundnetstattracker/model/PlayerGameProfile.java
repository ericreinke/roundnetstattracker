package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "player_profiles")
public class PlayerGameProfile implements Parcelable {

    @PrimaryKey
    @NonNull
    public String uid;

    public String playerName;

    public String playerId;

    public int totalFirstServe = 0;

    public int totalSecondServe = 0;

    public int firstServeFault = 0;

    public int secondServeFault = 0;

    public int ace = 0;

    public int aced = 0;

    public int putAwayFailure = 0;

    public int putAwaySuccess = 0;

    public int error = 0;

    public int defensiveTouch = 0;

    public int defensiveGet = 0;

    public PlayerGameProfile(String uid, String playerName, String playerId){
        this.uid = uid;
        this.playerName = playerName;
        this.playerId = playerId;
    }

    @Ignore
    public PlayerGameProfile(Parcel parcel) {
        uid = parcel.readString();
        playerName = parcel.readString();
        playerId = parcel.readString();
        totalFirstServe = parcel.readInt();
        totalSecondServe = parcel.readInt();
        firstServeFault = parcel.readInt();
        secondServeFault = parcel.readInt();
        ace = parcel.readInt();
        aced = parcel.readInt();
        putAwayFailure = parcel.readInt();
        putAwaySuccess = parcel.readInt();
        error = parcel.readInt();
        defensiveTouch = parcel.readInt();
        defensiveGet = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(playerName);
        parcel.writeString(playerId);
        parcel.writeInt(totalFirstServe);
        parcel.writeInt(totalSecondServe);
        parcel.writeInt(firstServeFault);
        parcel.writeInt(secondServeFault);
        parcel.writeInt(ace);
        parcel.writeInt(aced);
        parcel.writeInt(putAwayFailure);
        parcel.writeInt(putAwaySuccess);
        parcel.writeInt(error);
        parcel.writeInt(defensiveTouch);
        parcel.writeInt(defensiveGet);
    }

    public static final Parcelable.Creator<PlayerGameProfile> CREATOR =
            new Creator<PlayerGameProfile>() {
                @Override
                public PlayerGameProfile createFromParcel(Parcel parcel) {
                    return new PlayerGameProfile(parcel);
                }

                @Override
                public PlayerGameProfile[] newArray(int i) {
                    return new PlayerGameProfile[i];
                }
            };
}
