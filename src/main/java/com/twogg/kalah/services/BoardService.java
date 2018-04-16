package com.twogg.kalah.services;

import com.twogg.kalah.entities.Board;
import com.twogg.kalah.entities.Log;
import com.twogg.kalah.entities.Pit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private LogService logService;

    public Board getInitialBoard() {
        return new Board();
    }

    public String getInitialBoardHtml(Board board, String gameId) {
        return "<html>"
                + "<br>New game created GAME-ID: <b>"+gameId+"</b> first turn -> [Player" + board.getNextTurnPlayer()+"]"
                + "<br>" + getHtmlFormattedBoard(board.getPits()) + "</html>";
    }

    public String getBoardStatusHtml(String gameId) {
        Board board = loadBoard(gameId);
        if (board.getGameFinished()){
            return "<html>"
                    + "<br>Loaded GAME-ID: <b>"+gameId+"</b> is finished!"
                    + "<br>" + getHtmlFormattedBoard(board.getPits())
                    + "<br><br>Messages: " + board.getMessage()
                    + "</html>";
        } else {return "<html>"
                + "<br>Loaded GAME-ID: <b>"+gameId+"</b> next turn -> [Player" + board.getNextTurnPlayer()+"]"
                + "<br>" + getHtmlFormattedBoard(board.getPits()) + "</html>";
        }
    }

    public String playTurnHtml(Board board, String gameId) {
        if (board.getGameFinished()){
            return "<html>"
                    + "<br> GAME-ID: <b>"+gameId+"</b> is finished!"
                    + "<br>" + getHtmlFormattedBoard(board.getPits())
                    + "<br><br>Messages: " + board.getMessage()
                    + "</html>";
        } else {
            return "<html>"
                    + "<br>Playing GAME-ID: <b>"+gameId+"</b>"
                    + "<br>" + getHtmlFormattedBoard(board.getPits())
                    + "<br><br>Messages: " + board.getMessage() +", next turn -> [Player" + board.getNextTurnPlayer()+"]"
                    + "</html>";
        }
    }

    public boolean validateGameId(String gameId) {
        return (CollectionUtils.isEmpty(logService.findByGameId(gameId)) ? false : true );
    }

    public Board loadBoard(String gameId) {
        List<Log> list = logService.findByGameId(gameId);
        Log gameLoaded = list.stream().
                filter(x -> x.getGameId().equals(gameId))
                .sorted((f1, f2) -> Long.compare(f2.getCreateDate(), f1.getCreateDate()))
                .findFirst().get();
        return new Board(gameLoaded.getBoardStatus(), gameLoaded.getNextTurnGoes());
    }

    private String getHtmlFormattedBoard(List<Pit> board) {
        String currentBoard = "_____________________________________________________________________"
                + "<br>|____P2___|________|________|________|________|________|________|___P1___|"
                + "<br>|_________|__L:q__|__K:p__|___J:o__|__I:n___|__H:k__|__G:j__|________|"
                + "<br>|__K2:s__|________|________|________|________|________|________|_K1:i__|"
                + "<br>|_________|__A:a__|__B:c__|__C:d__|__D:e__ |__E:f__|__F:g__|________| ";

        String[] replace = {"a", "c", "d", "e", "f", "g", "i", "j", "k", "n", "o", "p", "q", "s"};
        for (int i = 0; i < Board.PITS_COUNT; i++) {
            String stones = (board.get(i).getStones() < 10 ? "0" + board.get(i).getStones() : "" + board.get(i).getStones());
            currentBoard = currentBoard.replace(replace[i], stones);
        }
        return currentBoard;
    }
}
