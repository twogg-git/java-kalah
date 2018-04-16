package com.twogg.kalah.controllers;

import com.twogg.kalah.entities.Board;
import com.twogg.kalah.entities.Log;
import com.twogg.kalah.services.BoardService;
import com.twogg.kalah.services.GameService;
import com.twogg.kalah.services.LogService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/v1/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = "application/JSON")
    public List<Log> getAllLogs() {
        List<Log> list = (List<Log>) logService.findAll();
        return list.stream().sorted((f1, f2) -> Long.compare(f2.getCreateDate(), f1.getCreateDate())).collect(toList());
    }

    @RequestMapping(value = "/new-board", method = RequestMethod.POST)
    public Object getInitialBoard() {
        Board initialBoard = boardService.getInitialBoard();
        Log log = new Log(initialBoard.getBoardStatus(), initialBoard.getNextTurnPlayer(), initialBoard.getMessage());
        logService.save(log);
        return boardService.getInitialBoardHtml(initialBoard, log.getGameId());
    }

    @RequestMapping(value = "/play", method = RequestMethod.POST)
    public Object playerTurn(
            @RequestParam(value = "game_id", required = true) String gameId,
            @RequestParam(value = "player_id", required = true) int playerId,
            @RequestParam(value = "pit_id", required = true) String pitId) {
        if (boardService.validateGameId(gameId)) {
            if (playerId >= 1 && playerId <= 2) {
                Board board = gameService.playTurn(gameId, playerId, pitId.toUpperCase().charAt(0));
                Log log = new Log(gameId, playerId, board.getNextTurnPlayer(), board.getBoardStatus(), board.getMessage());
                logService.save(log);
                return boardService.playTurnHtml(board, log.getGameId());
            } else {
                return "<html><br> ERROR - Invalid Player ID P[" + playerId + "], only gaming Player 1 and Player 2</html>";
            }
        } else {
            return "<html><br>ERROR! - Invalid GAME-ID: " + gameId + "</html>";
        }
    }

    @RequestMapping(value = "/load-board", method = RequestMethod.GET)
    public Object getBoardStatus(
            @RequestParam(value = "load_game_id", required = true) String gameId) {
        if (boardService.validateGameId(gameId)) {
            return boardService.getBoardStatusHtml(gameId);
        } else {
            return "<html><br>ERROR! - Invalid GAME-ID: <b>" + gameId + "</b></html>";
        }
    }

}
