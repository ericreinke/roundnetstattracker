package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerGameProfile implements Parcelable {

    private String name;
    private int firstServe = 0;
    private int secondServe = 0;
    private int firstServeFault = 0;
    private int secondServeFault = 0;
    private int ace = 0;
    private int aced = 0;
    private int putAwayOpportunity = 0;
    private int putAwaySuccess = 0;
    private int error = 0;
    private int defensiveTouch = 0;
    private int defensiveGet = 0;


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

    public int getSecondServe() { return secondServe; }

    public int getFirstServeFault() { return firstServeFault; }

    public int getSecondServeFault() { return secondServeFault; }

    public int getAce() { return ace; }

    public int getAced() { return aced; }

    public int getPutAwayOpportunity() { return putAwayOpportunity; }

    public int getPutAwaySuccess() { return putAwaySuccess; }

    public int getError() { return error; }

    public int getDefensiveTouch() { return defensiveTouch; }

    public int getDefensiveGet() { return defensiveGet; }

    public void addFirstServe() {this.firstServe++; }

    public void addSecondServe() { this.secondServe++; }

    public void addFirstServeFault() { this.firstServeFault++; }

    public void addSecondServeFault() { this.secondServeFault++; }

    public void addAce() { this.ace++; }

    public void addAced() { this.aced++; }

    public void addPutAwayFailure() { this.putAwayOpportunity++; }

    public void addPutAwaySuccess() { this.putAwaySuccess++; }

    public void addError() { this.error++; }

    public void addDefensiveTouch() { this.defensiveTouch++; }

    public void addDefensiveGet() { this.defensiveGet++; }

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
