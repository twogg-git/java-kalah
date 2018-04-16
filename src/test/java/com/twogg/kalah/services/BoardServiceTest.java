package com.twogg.kalah.services;

import com.twogg.kalah.entities.Board;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardServiceTest {

    @Before
    public void before(){
    }

    @Test
    public void getInitialBoard_assignGameIdPlayerAndStonesOK() {
        Board board = new Board(new int [] {0,55,0,0,0,0,0,0,0,0,0,0,0,9}, 1);
        BoardService tester = new BoardService();
        String response = tester.getInitialBoardHtml(board,"test");
        Assert.assertTrue(response.contains(" GAME-ID: <b>test</b>"));
        Assert.assertTrue(response.contains("-> [Player1]"));
        Assert.assertTrue(response.contains("|__B:55__|"));
        Assert.assertTrue(response.contains("|__K2:09__|"));
    }

    @Test
    public void playTurnHtml_gameFinishedK2Has11Stones() {
        Board board = new Board(new int [] {0,0,0,0,0,0,0,0,0,0,0,0,0,11}, 1);
        board.setGameFinished(true);
        BoardService tester = new BoardService();
        String response = tester.playTurnHtml( board,"test");
        Assert.assertTrue(response.contains(" GAME-ID: <b>test</b> is finished!"));
        Assert.assertTrue(response.contains("|__K2:11__|"));
    }

    @Test
    public void playTurnHtml_playsP2PitLHas3Stones() {
        Board board = new Board(new int [] {0,0,0,0,0,0,0,0,0,0,0,0,3,0}, 2);
        BoardService tester = new BoardService();
        String response = tester.playTurnHtml( board,"testp1");
        Assert.assertTrue(response.contains("Playing GAME-ID: <b>testp1</b>"));
        Assert.assertTrue(response.contains("-> [Player2]"));
        Assert.assertTrue(response.contains("|__L:03__|"));
    }
}