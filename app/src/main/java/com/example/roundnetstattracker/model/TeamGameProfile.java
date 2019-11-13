package com.example.roundnetstattracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "team_profiles")
public class TeamGameProfile implements Parcelable {

    @Ignore
    public PlayerGameProfile playerGameProfile1;
    @Ignore
    public PlayerGameProfile playerGameProfile2;

    @PrimaryKey
    @NonNull
    public String uid = UUID.randomUUID().toString();
    public int teamScore = -1;
    public String name;


    public TeamGameProfile(String teamName){
        playerGameProfile1 = new PlayerGameProfile("playerGameProfile1");
        playerGameProfile2 = new PlayerGameProfile("playerGameProfile2");
        String name = teamName;
    }

    public TeamGameProfile(){}
    /*
    Get team name.
     */
    public String getName(){
        return this.name;
    }

    /*
    Set team name.  In the future, retrieve from db
     */
    public void setName(String name){
        this.name = name;
    }

    /*
    This should be called only once when trying to save (db purposes).
    For dynamic use, use the game GameManager
     */
    public void setTeamScore(int score){ this.teamScore = score; }


    /*
    Parcel methods
     */

    public TeamGameProfile(Parcel parcel) {
        playerGameProfile1 = parcel.readParcelable(PlayerGameProfile.class.getClassLoader());
        playerGameProfile2 = parcel.readParcelable(PlayerGameProfile.class.getClassLoader());
        teamScore = parcel.readInt();
        name = parcel.readString();
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
