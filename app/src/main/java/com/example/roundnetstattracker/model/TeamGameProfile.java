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

    @PrimaryKey
    @NonNull
    public String uid;

    public String teamName;

    public String playerGameProfile1Id;

    public String playerGameProfile2Id;

    public String teamUid;

    public int teamScore = -1;


    public TeamGameProfile(String uid, String teamName, String playerGameProfile1Id, String playerGameProfile2Id, String teamUid, int teamScore){
        this.uid = uid;
        this.teamName = teamName;
        this.playerGameProfile1Id = playerGameProfile1Id;
        this.playerGameProfile2Id = playerGameProfile2Id;
        this.teamUid = teamUid;
        this.teamScore = teamScore;
    }

    /*
    Parcel methods
     */
    @Ignore
    public TeamGameProfile(Parcel parcel) {
        uid = parcel.readString();
        teamName = parcel.readString();
        playerGameProfile1Id = parcel.readString();
        playerGameProfile2Id = parcel.readString();
        teamUid = parcel.readString();
        teamScore = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(teamName);
        parcel.writeString(playerGameProfile1Id);
        parcel.writeString(playerGameProfile2Id);
        parcel.writeString(teamUid);
        parcel.writeInt(teamScore);
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
