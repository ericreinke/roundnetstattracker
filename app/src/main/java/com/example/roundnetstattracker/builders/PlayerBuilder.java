package com.example.roundnetstattracker.builders;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.example.roundnetstattracker.model.Player;

import java.util.UUID;

public class PlayerBuilder {

    public String uid = UUID.randomUUID().toString();

    public String name = "";

    public Player build(){
        assert(!name.isEmpty());
        return new Player(
                this.uid,
                this.name
        );
    }

    public PlayerBuilder withName(String name){
        this.name = name;
        return this;
    }

}
