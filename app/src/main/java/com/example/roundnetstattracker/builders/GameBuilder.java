package com.example.roundnetstattracker.builders;

import com.example.roundnetstattracker.model.Game;
import com.example.roundnetstattracker.model.GameManager;

import java.util.UUID;

public class GameBuilder {

    public  String uid = UUID.randomUUID().toString();

    public String teamAId = "";

    public String teamBId = "";

    public  String teamAProfileId = "";

    public  String teamBProfileId = "";

    public GameManager gm = new GameManager();

    public Game build(){
        assert(!teamAId.isEmpty());
        assert(!teamBId.isEmpty());
        assert(!teamAProfileId.isEmpty());
        assert(!teamBProfileId.isEmpty());
        return new Game(
            this.uid,
            this.teamAId,
            this.teamBId,
            this.teamAProfileId,
            this.teamBProfileId,
            this.gm
        );
    }

    public GameBuilder withTeamAId(String id){
        this.teamAId = id;
        return this;
    }

    public GameBuilder withTeamBId(String id){
        this.teamBId = id;
        return this;
    }

    public GameBuilder withTeamAProfileId(String id){
        this.teamAProfileId = id;
        return this;
    }

    public GameBuilder withTeamBProfileId(String id){
        this.teamBProfileId = id;
        return this;
    }
}
