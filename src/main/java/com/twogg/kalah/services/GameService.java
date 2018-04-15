package com.twogg.kalah.services;

import com.twogg.kalah.entities.Board;
import com.twogg.kalah.entities.Pit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    public Board getInitialBoard() {
        return new Board();
    }

    public Board getBoardStatus(String gameId) {
        return loadBoard(gameId);
    }

    public String getInitialBoardHtml() {
        Board board = new Board();
        return "<html>" + board.getCurrentTurn()
                + "<br>" + getHtmlFormattedBoard(board.getPits())
                + "<br><br>Next turn is for: Player" + board.getNextTurnPlayer()
                + "<br>Messages: " + board.getMessage()
                + "<a href='http://localhost:8080?game_id=20180415-06:32'>Start playing!</a>"
                + "</html>";
    }

    public String getBoardStatusHtml(String gameId) {
        Board board = loadBoard(gameId);
        return "<html>" + board.getCurrentTurn()
                + "<br>" + getHtmlFormattedBoard(board.getPits())
                + "<br><br>Next turn is for: Player" + board.getNextTurnPlayer()
                + "<br>Messages: " + board.getMessage()
                + "<a href='http://localhost:8080?game_id=20180415-06:32'>Start playing!</a>"
                + "</html>";
    }

    public String playTurnHtml(String gameId, int playerId, char pitChar) {
        Board board = playTurn(gameId, playerId, pitChar);
        return "<html>" + board.getCurrentTurn()
                + "<br>" + getHtmlFormattedBoard(board.getPits())
                + "<br><br>Next turn is for: Player" + board.getNextTurnPlayer()
                + "<br>Messages: " + board.getMessage()
                + "<a href='http://localhost:8080?game_id=20180415-06:32'>Start playing!</a>"
                + "</html>";
    }

    public Board playTurn(String gameId, int playerId, char pitChar) {

        Board board = loadBoard(gameId);
        if (playerId < 1 || playerId > 2 || board.getNextTurnPlayer() != playerId) {
            board.setCurrentTurn("Plays: [ERROR] Selected Pit: [ERROR]");
            board.setMessage("Invalid Player ID! [" + playerId + "] Log for Player" + board.getNextTurnPlayer());
            return board;
        }

        int pitIndex = Pit.getIndexByAlpha(pitChar);
        if (pitIndex == -1) {
            board.setCurrentTurn("Plays: [P" + playerId + "] Selected Pit: [ERROR]");
            board.setMessage("Invalid pit index! [" + pitChar + "] Log remains on Player" + playerId);
            return board;
        }

        if(board.getPits().get(pitIndex).getOwner() != playerId){
            board.setCurrentTurn("Plays: [P" + playerId + "] Selected Pit: [" + pitChar + ":" + pitIndex + "]");
            board.setMessage("Invalid pit index! [" + pitChar + "] Does not belong to this player!");
            return board;
        }

        // starts turn, distribute stones on adjacent pits
        board.setCurrentTurn("Plays: [P" + playerId + "] Selected Pit: [" + pitChar + ":" + pitIndex + "]");
        board =  distributePitStones(board, pitIndex);

        // validates if player gets free turn
        if (board.getPits().get(board.getLastPit()).getIsKalah()) {
            board.setNextTurnPlayer(playerId);
            board.setMessage("Last Stone ends in Player" + playerId+" Kalah's, gets free turn!");
        } else {
            board = captureStones(board, playerId);
        }

        // validates remaining stones per player or possible end game
        board = validateRemainingStones(board);
        return board;
    }

    private Board distributePitStones(Board board, int pitIndex){
        List<Pit> pits = board.getPits();
        int stones = pits.get(pitIndex).getStones();
        board.setLastPit(-1);
        pits.get(pitIndex).emptyPit();
        for (int i = 0, j = pitIndex + 1; i < stones; i++, j++) {
            if (j == Board.PITS_COUNT) {
                j = 0;
            }
            pits.get(j).addOneStone();
            board.setLastPit(j);
        }
        board.setNextTurnPlayer(2);
        board.setMessage("Distributed "+stones+" stones, from ["+Pit.getAlphaByIndex(pitIndex)+":"+pitIndex+"] till ["+Pit.getAlphaByIndex(board.getLastPit())+":"+board.getLastPit()+"]");
        return board;
    }

    private Board captureStones(Board board, int playerId) {
        List<Pit> pits = board.getPits();
        if (pits.get(board.getLastPit()).getStones() == 1 && pits.get(board.getLastPit()).getOwner() == playerId) {
            int oppositeIndex = pits.get(board.getLastPit()).getOpposite();
            int oppositeStones = pits.get(oppositeIndex).getStones();
            pits.get(oppositeIndex).emptyPit();
            pits.get(board.getLastPit()).emptyPit();
            pits.get((playerId == 1 ? Board.K1_INDEX : Board.K2_INDEX)).addCaptureStones(oppositeStones + 1);
            board.setMessage("Player" + playerId + " capture opposite pit stone's, ends his turn.");
            board.setNextTurnPlayer((playerId == 1 ? 2 : 1));
        }
        return board;
    }

    private Board validateRemainingStones(Board board) {
        List<Pit> pits = board.getPits();
        int stonesP1 = 0;
        for (int i = 0; i < Board.K1_INDEX - 1; i++) {
            stonesP1 += pits.get(i).getStones();
        }
        if (stonesP1 == 0) {
            int remainingStones = 0;
            for (int i = Board.K1_INDEX + 1; i < Board.PITS_COUNT; i++) {
                remainingStones += pits.get(i).getStones();
                pits.get(i).emptyPit();
            }
            pits.get(Board.K2_INDEX).addCaptureStones(remainingStones);
            board.setMessage("Game ends! Player1 has no remaining stones!");
            board.setNextTurnPlayer(-1);
        }

        int stonesP2 = 0;
        for (int i = Board.K1_INDEX + 1; i < Board.PITS_COUNT; i++) {
            stonesP2 += pits.get(i).getStones();
        }
        if (stonesP2 == 0) {
            for (int i = 0; i < Board.K1_INDEX - 1; i++) {
                pits.get(i).emptyPit();
            }
            pits.get(Board.K1_INDEX).addCaptureStones(stonesP1);
            board.setMessage("Game ends! Player2 has no remaining stones!");
            board.setNextTurnPlayer(-1);
        }
        return board;
    }

    private Board loadBoard(String gameId) {
        return new Board();
    }

    private String getHtmlFormattedBoard(List<Pit> board) {
        String currentBoard = "____________________________________________________"
                + "<br>|__P2__|______|______|______|______|______|______|__P1__|"
                + "<br>|______|__q__|__p__|__o__|__n__|__k__|__j__|______|"
                + "<br>|__s__|______|______|______|______|______|______|__i__|"
                + "<br>|______|__a__|__c__|__d__|__e__|__f__|__g__|______| ";

        String[] replace = {"a", "c", "d", "e", "f", "g", "i", "j", "k", "n", "o", "p", "q", "s"};
        for (int i = 0; i < Board.PITS_COUNT; i++) {
            String stones = (board.get(i).getStones() < 10 ? "0" + board.get(i).getStones() : "" + board.get(i).getStones());
            currentBoard = currentBoard.replace(replace[i], stones);
        }
        return currentBoard;
    }


}
