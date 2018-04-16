package com.twogg.kalah.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties
public class Board {

    @Value("${stones.by.pit}")
    private int stonesByPit = 6;

    @Value("${starts.player}")
    private int startsPlayer = 1;

    public static final int PITS_COUNT = 14;
    public static final int K1_INDEX = 6;
    public static final int K2_INDEX = 13;

    private List<Pit> pits;
    private String message;
    private int nextTurnPlayer;
    private int lastPit;
    private boolean gameFinished;

    public Board(){
        this.pits = new ArrayList<>(PITS_COUNT);
        for (int i = 0; i < PITS_COUNT; i++) {
            Pit pit = new Pit(i, ( (i == 6 || i == 13) ?  0 : stonesByPit));
            this.pits.add(pit);
        }
        this.nextTurnPlayer = startsPlayer;
        this.message = "Board ready!, first turn is for Player"+startsPlayer+"...";
    }

    public Board(int[] stones, int nextTurnPlayer){
        this.pits = new ArrayList<>(PITS_COUNT);
        for (int i = 0; i < PITS_COUNT; i++) {
            Pit pit = new Pit(i, stones[i]);
            this.pits.add(pit);
        }
        this.nextTurnPlayer = nextTurnPlayer;
        this.message = "Board loaded!...";
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

    public int getLastPit() {
        return lastPit;
    }

    public void setLastPit(int lastPit) {
        this.lastPit = lastPit;
    }

    public int[] getBoardStatus() {
        int[] boardStatus = new int[PITS_COUNT];
        for (int i = 0; i< PITS_COUNT; i++){
            boardStatus[i] = pits.get(i).getStones();
        }
        return boardStatus;
    }

    public boolean getGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
}
