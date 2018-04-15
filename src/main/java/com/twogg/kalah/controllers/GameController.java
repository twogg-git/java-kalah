package com.twogg.kalah.controllers;

import com.twogg.kalah.entities.Board;
import com.twogg.kalah.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/{game-id}/play/{player-id}", method = RequestMethod.GET)
    public Board playerTurn(
            @PathVariable("game-id") int gameId,
            @PathVariable("player-id") int playerId,
            @RequestParam(value = "pit") String pit){
        return gameService.playTurn(gameId, playerId, pit.charAt(0));
    }

    @RequestMapping(value = "/{game-id}/board-status", method = RequestMethod.GET)
    public Board getBoardStatus(
            @PathVariable("game-id") int gameId){
        return gameService.getBoardStatus(gameId);
    }

    @RequestMapping(value = "/initial-board", method = RequestMethod.GET)
    public Board getInitialBoard(){
        return gameService.getInitialBoard();
    }

    @RequestMapping(value = "/{game-id}/html/play/{player-id}", method = RequestMethod.GET)
    public String playerTurnHtml(
            @PathVariable("game-id") int gameId,
            @PathVariable("player-id") int playerId,
            @RequestParam(value = "pit") String pit){
        return gameService.playTurnHtml(gameId, playerId, pit.charAt(0));
    }
    @RequestMapping(value = "/{game-id}/html/board-status", method = RequestMethod.GET)
    public String getBoardStatusHtml(
            @PathVariable("game-id") int gameId){
        return gameService.getBoardStatusHtml(gameId);
    }

    @RequestMapping(value = "/html/initial-board", method = RequestMethod.GET)
    public String getInitialBoardHtml(){
        return gameService.getInitialBoardHtml();
    }


}
