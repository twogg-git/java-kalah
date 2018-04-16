package com.twogg.kalah.services;

import com.twogg.kalah.entities.Board;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anySetOf;

public class GameServiceTest {

    @Before
    public void before(){

    }

    @Test
    public void distributePitStones_FixedDistributionP1FromPit2() {
        Board fixedP1Board = new Board(new int [] {0,0,5,0,0,0,0,0,0,0,0,0,0,0}, 1);
        GameService tester = new GameService();
        Board response = tester.distributePitStones(fixedP1Board,1,2);
        Assert.assertArrayEquals(new int [] {0,0,0,1,1,1,1,1,0,0,0,0,0,0}, response.getBoardStatus());
    }

    @Test
    public void distributePitStones_FixedDistributionP1FromPit2Stones15() {
        Board fixedP1Board = new Board(new int [] {0,0,15,0,0,0,0,0,0,0,0,0,0,0}, 1);
        GameService tester = new GameService();
        Board response = tester.distributePitStones(fixedP1Board,1,2);
        Assert.assertArrayEquals(new int [] {1,1,1,2,1,1,1,1,1,1,1,1,1,0}, response.getBoardStatus());
    }

    @Test
    public void distributePitStones_FixedDistributionP1FromPit5Stones1() {
        Board fixedP1Board = new Board(new int [] {0,0,0,0,0,1,0,0,0,0,0,0,0,0}, 1);
        GameService tester = new GameService();
        Board response = tester.distributePitStones(fixedP1Board,1,5);
        Assert.assertArrayEquals(new int [] {0,0,0,0,0,0,1,0,0,0,0,0,0,0}, response.getBoardStatus());
    }

    @Test
    public void distributePitStones_FixedDistributionP2FromPit12Stones3() {
        Board fixedP1Board = new Board(new int [] {0,0,0,0,0,0,0,0,0,0,0,0,3,0}, 2);
        GameService tester = new GameService();
        Board response = tester.distributePitStones(fixedP1Board,2,12);
        Assert.assertArrayEquals(new int [] {1,1,0,0,0,0,0,0,0,0,0,0,0,1}, response.getBoardStatus());
    }

    @Test
    public void endsInPlayerKalah_endsInP1PitF() {
        Board board = new Board(new int [] {6,6,6,6,6,6,0,6,6,6,6,6,6,0}, 1);
        board.setLastPit(5);
        GameService tester = new GameService();
        Board response = tester.endsInPlayerKalah(board, 1);
        Assert.assertEquals(2, response.getNextTurnPlayer());
    }

    @Test
    public void endsInPlayerKalah_endsInP1PitK2() {
        Board board = new Board(new int [] {6,6,6,6,6,6,0,6,6,6,6,6,6,0}, 1);
        board.setLastPit(13);
        GameService tester = new GameService();
        Board response = tester.endsInPlayerKalah(board, 1);
        Assert.assertEquals(2, response.getNextTurnPlayer());
    }

    @Test
    public void endsInPlayerKalah_endsInP1PitK1() {
        Board board = new Board(new int [] {6,6,6,6,6,6,0,6,6,6,6,6,6,0}, 1);
        board.setLastPit(6);
        GameService tester = new GameService();
        Board response = tester.endsInPlayerKalah(board, 1);
        Assert.assertEquals(1, response.getNextTurnPlayer());
    }

    @Test
    public void captureStones_playP1Pit4PCapure5Stones() {
        Board board = new Board(new int [] {0,0,0,0,0,1,0,5,0,0,0,0,0,0}, 1);
        board.setLastPit(5);
        GameService tester = new GameService();
        Board response = tester.captureStones(board, 1);
        Assert.assertEquals(6, response.getPits().get(6).getStones());
    }

    @Test
    public void captureStones_playP1Pit4PCapure0Stones() {
        Board board = new Board(new int [] {0,0,0,0,0,1,0,0,6,0,0,0,0,0}, 1);
        board.setLastPit(5);
        GameService tester = new GameService();
        Board response = tester.captureStones(board, 1);
        Assert.assertEquals(0, response.getPits().get(0).getStones());
    }

    @Test
    public void captureStones_playP2Pit12PCapure13Stones() {
        Board board = new Board(new int [] {12,0,0,0,0,0,0,0,0,0,0,0,1,1}, 1);
        board.setLastPit(12);
        GameService tester = new GameService();
        Board response = tester.captureStones(board, 2);
        Assert.assertEquals(14, response.getPits().get(13).getStones());
    }

    @Test
    public void validateRemainingStones_playsP1WinsP1With2Stones() {
        Board board = new Board(new int [] {1,1,0,0,0,0,0,0,0,0,0,0,0,0}, 1);
        board.setNextTurnPlayer(2);
        GameService tester = new GameService();
        Board response = tester.validateRemainingStones(board);
        Assert.assertEquals(true, response.getGameFinished());
        Assert.assertEquals(2, response.getPits().get(6).getStones());
        Assert.assertTrue(response.getMessage().contains("Wins Player1"));
    }

    @Test
    public void validateRemainingStones_playsP1WinsP2With5Stones() {
        Board board = new Board(new int [] {0,0,0,0,0,0,1,0,0,1,0,1,0,3}, 1);
        board.setNextTurnPlayer(2);
        GameService tester = new GameService();
        Board response = tester.validateRemainingStones(board);
        Assert.assertEquals(true, response.getGameFinished());
        Assert.assertEquals(5, response.getPits().get(13).getStones());
        Assert.assertTrue(response.getMessage().contains("Wins Player2"));
    }

    @Test
    public void validateRemainingStones_playsP1KeepsP2Playing() {
        Board board = new Board(new int [] {1,0,0,0,0,0,0,1,0,0,0,0,0,0}, 1);
        board.setNextTurnPlayer(2);
        GameService tester = new GameService();
        Board response = tester.validateRemainingStones(board);
        Assert.assertEquals(false, response.getGameFinished());
    }
}