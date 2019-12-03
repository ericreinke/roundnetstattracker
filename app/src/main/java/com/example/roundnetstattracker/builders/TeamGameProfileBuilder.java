package com.example.roundnetstattracker.builders;

import com.example.roundnetstattracker.model.TeamGameProfile;

import java.util.UUID;

public class TeamGameProfileBuilder {

    public String uid = UUID.randomUUID().toString();

    public String teamName = "";

    public String teamId = "";

    public String playerGameProfile1Id = "";

    public String playerGameProfile2Id = "";


    public int teamScore = -1;

    public TeamGameProfile build(){
        assert(!teamId.isEmpty());
        assert(!playerGameProfile1Id.isEmpty());
        assert(!playerGameProfile2Id.isEmpty());
        return new TeamGameProfile(
                this.uid,
                this.teamName,
                this.playerGameProfile1Id,
                this.playerGameProfile2Id,
                this.teamId,
                this.teamScore
        );
    }

    public TeamGameProfileBuilder withPlayerGameProfile1Id(String id){
        this.playerGameProfile1Id = id;
        return this;
    }

    public TeamGameProfileBuilder withPlayerGameProfile2Id(String id){
        this.playerGameProfile2Id = id;
        return this;
    }

    public TeamGameProfileBuilder withTeamId(String id){
        this.teamId = id;
        return this;
    }

    public TeamGameProfileBuilder withTeamScore(int score){
        this.teamScore = score;
        return this;
    }

    public TeamGameProfileBuilder withTeamName(String name){
        this.teamName = name;
        return this;
    }
}
