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

    @RequestMapping(value = "/initial-board", method = RequestMethod.GET)
    public Object getInitialBoard(
            @RequestParam(value = "in-html", defaultValue = "false") boolean inHtml){
        if(inHtml ==  true) {
            return gameService.getInitialBoardHtml();
        } else {
            return gameService.getInitialBoard();
        }
    }

    @RequestMapping(value = "/{game-id}/play/{player-id}", method = RequestMethod.GET)
    public Object playerTurn(
            @PathVariable("game-id") int gameId,
            @PathVariable("player-id") int playerId,
            @RequestParam(value = "pit-id") char pitId,
            @RequestParam(value = "in-html", defaultValue = "false") boolean inHtml){
        if(inHtml ==  true) {
            return gameService.playTurnHtml(gameId, playerId, pitId);
        } else{
            return gameService.playTurn(gameId, playerId, pitId);
        }
    }

    @RequestMapping(value = "/{game-id}/board-status", method = RequestMethod.GET)
    public Object getBoardStatus(
            @PathVariable("game-id") int gameId,
            @RequestParam(value = "in-html", defaultValue = "false") boolean inHtml){
        if(inHtml ==  true) {
            return gameService.getBoardStatusHtml(gameId);
        } else {
            return gameService.getBoardStatus(gameId);
        }
    }

}
