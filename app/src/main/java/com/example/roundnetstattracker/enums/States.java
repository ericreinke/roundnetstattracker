package com.example.roundnetstattracker.enums;

public enum States {
    SERVING("serving"),
    RECEIVING("receiving"),
    ERROR("error"),
    SETUP("setup");

    private String state;

    States (String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
