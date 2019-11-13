package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "player_profiles")
public class PlayerGameProfile implements Parcelable {

    @PrimaryKey
    @NonNull
    public String uidString = UUID.randomUUID().toString();

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "first_serve")
    public int firstServe = 0;

    @ColumnInfo(name = "second_serve")
    public int secondServe = 0;

    @ColumnInfo(name = "first_serve_fault")
    public int firstServeFault = 0;

    @ColumnInfo(name = "second_serve_fault")
    public int secondServeFault = 0;

    @ColumnInfo(name = "ace")
    public int ace = 0;

    @ColumnInfo(name = "aced")
    public int aced = 0;

    @ColumnInfo(name = "put_away_opportunity")
    public int putAwayOpportunity = 0;

    @ColumnInfo(name = "put_away_success")
    public int putAwaySuccess = 0;

    @ColumnInfo(name = "error")
    public int error = 0;

    @ColumnInfo(name = "defensive_touch")
    public int defensiveTouch = 0;

    @ColumnInfo(name = "defensive_get")
    public int defensiveGet = 0;

    public PlayerGameProfile(){}

    public PlayerGameProfile(String playerName){
        name=playerName;
    }

    public PlayerGameProfile(Parcel parcel) {
        name = parcel.readString();
        firstServe = parcel.readInt();
        secondServe = parcel.readInt();
        firstServeFault = parcel.readInt();
        secondServeFault = parcel.readInt();
        ace = parcel.readInt();
        aced = parcel.readInt();
        putAwayOpportunity = parcel.readInt();
        putAwaySuccess = parcel.readInt();
        error = parcel.readInt();
        defensiveTouch = parcel.readInt();
        defensiveGet = parcel.readInt();
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
        parcel.writeInt(firstServe);
        parcel.writeInt(secondServe);
        parcel.writeInt(firstServeFault);
        parcel.writeInt(secondServeFault);
        parcel.writeInt(ace);
        parcel.writeInt(aced);
        parcel.writeInt(putAwayOpportunity);
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
