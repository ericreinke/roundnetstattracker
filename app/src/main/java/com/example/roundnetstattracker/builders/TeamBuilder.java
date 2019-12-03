package com.example.roundnetstattracker.builders;

import com.example.roundnetstattracker.model.Team;

import java.util.UUID;

public class TeamBuilder {

    public String uid = UUID.randomUUID().toString();

    public String name = "";

    public String player1Id = "";

    public String player2Id = "";

    public Team build(){
        assert(!name.isEmpty());
        assert(!player1Id.isEmpty());
        assert(!player2Id.isEmpty());
        return new Team(
                this.uid,
                this.name,
                this.player1Id,
                this.player2Id
        );
    }

    public TeamBuilder withName(String name){
        this.name = name;
        return this;
    }

    public TeamBuilder withPlayer1Id(String id){
        this.player1Id = id;
        return this;
    }

    public TeamBuilder withPlayer2Id(String id){
        this.player2Id = id;
        return this;
    }
}
