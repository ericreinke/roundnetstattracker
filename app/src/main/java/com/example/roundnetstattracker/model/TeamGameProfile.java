package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TeamGameProfile implements Parcelable {

    public PlayerGameProfile playerGameProfile1;
    public PlayerGameProfile playerGameProfile2;
    private String name;


    public TeamGameProfile(String teamName){
        playerGameProfile1 = new PlayerGameProfile("playerGameProfile1");
        playerGameProfile2 = new PlayerGameProfile("playerGameProfile2");
        String name = teamName;
    }

    public TeamGameProfile(Parcel parcel) {
        playerGameProfile1 = parcel.readParcelable(PlayerGameProfile.class.getClassLoader());
        playerGameProfile2 = parcel.readParcelable(PlayerGameProfile.class.getClassLoader());
        name = parcel.readString();
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
        parcel.writeParcelable(playerGameProfile1,0);
        parcel.writeParcelable(playerGameProfile2,0);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<TeamGameProfile> CREATOR =
            new Creator<TeamGameProfile>() {
                @Override
                public TeamGameProfile createFromParcel(Parcel parcel) {
                    return new TeamGameProfile(parcel);
                }

                @Override
                public TeamGameProfile[] newArray(int i) {
                    return new TeamGameProfile[i];
                }
            };
}
