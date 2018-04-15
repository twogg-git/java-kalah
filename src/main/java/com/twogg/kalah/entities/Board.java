package com.twogg.kalah.entities;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class Board {

    @Value("${stones_by_pit}")
    private int stonesByPit = 6;

    @Value("${starts_player}")
    private int startsPlayer = 1;

    public static final int PITS_COUNT = 14;
    public static final int K1_INDEX = 6;
    public static final int K2_INDEX = 13;

    private List<Pit> pits;
    private String message;
    private String currentTurn;
    private int nextTurnPlayer;
    private int lastPit;

    public Board(){
        pits = new ArrayList<>(PITS_COUNT);
        for (int i = 0; i < PITS_COUNT; i++) {
            Pit pit = new Pit(i, stonesByPit);
            pits.add(pit);
        }
        nextTurnPlayer = startsPlayer;
        currentTurn = "Game starts, first turn goes to: Player"+ nextTurnPlayer;
        message = "Board ready!...";
    }

    public List<Pit> getPits(){
        return pits;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNextTurnPlayer() {
        return nextTurnPlayer;
    }

    public void setNextTurnPlayer(int nextTurnPlayer) {
        this.nextTurnPlayer = nextTurnPlayer;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getLastPit() {
        return lastPit;
    }

    public void setLastPit(int lastPit) {
        this.lastPit = lastPit;
    }

    public void getFixedBoard(int[] stones) {
        for (int i = 0; i < PITS_COUNT; i++) {
            Pit pit = new Pit(i, stones[i]);
            pits.add(pit);
        }
    }
}
