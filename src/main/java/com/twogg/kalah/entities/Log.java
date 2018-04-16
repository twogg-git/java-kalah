package com.twogg.kalah.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Document(collection = "logs")
public class Log implements Serializable {

    @Id
    private String id;

    private String gameId;
    private int currentPlayer;
    private int nextTurnGoes;
    private String turnMessages;
    private int[] boardStatus;
    private Long createDate;

    public Log(){

    }

    public Log(int[] boardStatus, int nextTurnGoes, String turnMessages){
        this.gameId = new SimpleDateFormat("yyyyMMdd-hh:mm:ss").format(new Date( ));
        this.currentPlayer = 0;
        this.nextTurnGoes =  nextTurnGoes;
        this.boardStatus = boardStatus;
        this.turnMessages = turnMessages;
        this.createDate = new Date().getTime();
    }

    public Log(String gameId, int currentPlayer, int nextTurnGoes, int[] boardStatus, String turnMessages) {
        this.gameId = gameId;
        this.currentPlayer = currentPlayer;
        this.nextTurnGoes =  nextTurnGoes;
        this.boardStatus = boardStatus;
        this.turnMessages = turnMessages;
        this.createDate = new Date().getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getNextTurnGoes() {
        return nextTurnGoes;
    }

    public void setNextTurnGoes(int nextTurnGoes) {
        this.nextTurnGoes = nextTurnGoes;
    }

    public String getTurnMessages() {
        return turnMessages;
    }

    public void setTurnMessages(String turnMessages) {
        this.turnMessages = turnMessages;
    }

    public int[] getBoardStatus() {
        return boardStatus;
    }

    public void setBoardStatus(int[] boardStatus) {
        this.boardStatus = boardStatus;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(id, log.id) &&
                Objects.equals(gameId, log.gameId) &&
                Objects.equals(currentPlayer, log.currentPlayer) &&
                Objects.equals(nextTurnGoes, log.nextTurnGoes) &&
                Objects.equals(boardStatus, log.boardStatus) &&
                Objects.equals(turnMessages, log.turnMessages)&&
                Objects.equals(createDate, log.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameId, currentPlayer, nextTurnGoes, boardStatus, turnMessages, createDate);
    }
}
