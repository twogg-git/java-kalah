package com.twogg.kalah.controllers;

import com.twogg.kalah.entities.Board;
import com.twogg.kalah.entities.Log;
import com.twogg.kalah.services.GameService;
import com.twogg.kalah.services.LogService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = "application/JSON")
    public List<Log> getAllLogs() {
        return (List<Log>) logService.findAll();
    }

    @RequestMapping(value = "/{gameId}", produces = "application/JSON")
    public Optional<Log> findById(@PathVariable("gameId") String gameId) {
        return logService.findById(gameId);
    }

    @RequestMapping(value = "/initial-board", method = RequestMethod.POST)
    public Object getInitialBoard(
            @RequestParam(value = "in_html", defaultValue = "false") boolean inHtml){
        Board initialBoard = gameService.getInitialBoard();
        Log log = new Log(initialBoard.getBoardStatus(), initialBoard.getNextTurnPlayer(), initialBoard.getMessage());
        logService.save(log);
        if(inHtml ==  true) {
            return gameService.getInitialBoardHtml();
        } else {
            return gameService.getInitialBoard();
        }
    }

    @RequestMapping(value = "/play", method = RequestMethod.POST)
    public Object playerTurn(
            @RequestParam(value = "game_id", required = true) String gameId,
            @RequestParam(value = "player_id", required = true) int playerId,
            @RequestParam(value = "pit_id", required = true) char pitId,
            @RequestParam(value = "in_html", defaultValue = "false") boolean inHtml){

        Board board = gameService.playTurn(gameId, playerId, pitId);
        Log log = new Log(gameId, playerId, board.getNextTurnPlayer(), board.getBoardStatus(), board.getMessage());
        logService.save(log);

        if(inHtml ==  true) {
            return gameService.playTurnHtml(gameId, playerId, pitId);
        } else{
            return gameService.playTurn(gameId, playerId, pitId);
        }
    }

    @RequestMapping(value = "/{game-id}/board-status", method = RequestMethod.GET)
    public Object getBoardStatus(
            @PathVariable("game-id") String gameId,
            @RequestParam(value = "in-html", defaultValue = "false") boolean inHtml){
        if(inHtml ==  true) {
            return gameService.getBoardStatusHtml(gameId);
        } else {
            return gameService.getBoardStatus(gameId);
        }
    }

}
