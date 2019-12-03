package com.example.roundnetstattracker.builders;

import com.example.roundnetstattracker.model.PlayerGameProfile;

import java.util.UUID;

public class PlayerGameProfileBuilder {

    private String uid = UUID.randomUUID().toString();

    private String playerName;

    private String playerId = "";

    public PlayerGameProfile build(){
        assert(!playerId.isEmpty());
        return new PlayerGameProfile(
                this.uid,
                this.playerName,
                this.playerId
        );
    }

    public PlayerGameProfileBuilder withPlayerId(String playerId){
        this.playerId = playerId;
        return this;
    }

    public PlayerGameProfileBuilder withPlayerName(String playerName){
        this.playerName = playerName;
        return this;
    }
}
