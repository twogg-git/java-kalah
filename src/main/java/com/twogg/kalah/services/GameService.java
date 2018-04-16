package com.twogg.kalah.services;

import com.twogg.kalah.entities.Board;
import com.twogg.kalah.entities.Pit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  @Autowired
  private BoardService boardService;


    public Board playTurn(String gameId, int playerId, char pitChar) {

        Board boardPT = boardService.loadBoard(gameId);
        if (playerId < 1 || playerId > 2 || boardPT.getNextTurnPlayer() != playerId) {
            boardPT.setMessage("ERROR - Invalid Player ID P[" + playerId + "], is Player"+boardPT.getNextTurnPlayer()+" turn!");
            return boardPT;
        }

        int pitIndex = Pit.getIndexByAlpha(pitChar);
        if (pitIndex == -1) {
            String message = "Indexes for Player"+ playerId+ (playerId == 1 ? " are [ A - F ]" : " are [ G - L ]" );
            boardPT.setMessage("ERROR - Pit index [" + pitChar + "] does not exists. "+message+", turn remains on Player"+playerId);
            return boardPT;
        }

        if(boardPT.getPits().get(pitIndex).getOwner() != playerId){
            String message = "Indexes for Player"+ playerId+ (playerId == 1 ? " are [ A - F ]" : " are [ G - L ]" );
            boardPT.setMessage("ERROR - Invalid pit index [" + pitChar + "]. "+message+", turn remains on Player"+playerId);
            return boardPT;
        }

        if(boardPT.getPits().get(pitIndex).getStones() < 1){
            boardPT.setMessage("ERROR - Pit index [" + pitChar + "] has no stones, please select another one, turn remains on Player"+playerId);
            return boardPT;
        }

        // starts turn, distribute stones on adjacent pits
        boardPT = distributePitStones(boardPT, playerId, pitIndex);

        // validates if player gets free turn
        boardPT = endsInPlayerKalah(boardPT, playerId);

        // validates if player captures opposite stones
        boardPT =  captureStones(boardPT, playerId);

        // validates remaining stones per player or possible end game
        boardPT = validateRemainingStones(boardPT);
        return boardPT;
    }



    public Board distributePitStones(Board boardDP, int playerId, int pitIndex){
        int stones = boardDP.getPits().get(pitIndex).getStones();
        boardDP.setLastPit(-1);
        boardDP.getPits().get(pitIndex).emptyPit();
        for (int i = 0, j = pitIndex + 1; i < stones; i++, j++) {
            if (j == Board.PITS_COUNT) {
                j = 0;
            }
            if(!boardDP.getPits().get(j).getIsKalah()){
                boardDP.getPits().get(j).addOneStone();
                boardDP.setLastPit(j);
            } else if(boardDP.getPits().get(j).getOwner() == playerId){
                boardDP.getPits().get(j).addOneStone();
                boardDP.setLastPit(j);
            }
        }
        String tillPit = (Pit.getAlphaByIndex(boardDP.getLastPit()) == '1' ? "K1" : ""+Pit.getAlphaByIndex(boardDP.getLastPit()));
        boardDP.setMessage("Selected ["+Pit.getAlphaByIndex(pitIndex)+"], distributed "+stones+" stones from ["+Pit.getAlphaByIndex(pitIndex+1)+"] to ["+ tillPit +"] ends in ["+Pit.getAlphaByIndex(boardDP.getLastPit())+"]");
        return boardDP;
    }

    public Board endsInPlayerKalah(Board boardPK, int playerId){
        if (boardPK.getPits().get(boardPK.getLastPit()).getIsKalah() &&
                boardPK.getPits().get(boardPK.getLastPit()).getOwner() == playerId) {
            boardPK.setNextTurnPlayer(playerId);
            boardPK.setMessage(boardPK.getMessage()+". Last stone ends in K" + playerId+", player gets free turn!");
        } else {
            boardPK.setNextTurnPlayer((playerId == 1 ? 2 : 1));
        }
        return boardPK;
    }

    public Board captureStones(Board boardCP, int playerId) {
        if (boardCP.getPits().get(boardCP.getLastPit()).getStones() == 1
                && boardCP.getPits().get(boardCP.getLastPit()).getOwner() == playerId
                && !boardCP.getPits().get(boardCP.getLastPit()).getIsKalah()) {
            int oppositeIndex = boardCP.getPits().get(boardCP.getLastPit()).getOpposite();
            int oppositeStones = boardCP.getPits().get(oppositeIndex).getStones();
            boardCP.getPits().get(oppositeIndex).emptyPit();
            boardCP.getPits().get(boardCP.getLastPit()).emptyPit();
            boardCP.getPits().get((playerId == 1 ? Board.K1_INDEX : Board.K2_INDEX)).addCaptureStones(oppositeStones + 1);
            boardCP.setMessage(boardCP.getMessage() + ". Player" + playerId + " capture opposite pit stone's, ends his turn.");
            boardCP.setNextTurnPlayer((playerId == 1 ? 2 : 1));
        }
        return boardCP;
    }

    public Board validateRemainingStones(Board boardRS) {
        int stonesP1 = 0;
        for (int i = 0; i < Board.K1_INDEX - 1; i++) {
            stonesP1 += boardRS.getPits().get(i).getStones();
        }
        if (stonesP1 == 0) {
            int remainingStones = 0;
            for (int i = Board.K1_INDEX + 1; i < Board.PITS_COUNT; i++) {
                remainingStones += boardRS.getPits().get(i).getStones();
                boardRS.getPits().get(i).emptyPit();
            }
            boardRS.getPits().get(Board.K2_INDEX).addCaptureStones(remainingStones);
            boardRS.setMessage(boardRS.getMessage()
                    +" Game ends! Player1 has no remaining stones!."
                    + (boardRS.getPits().get(6).getStones() > boardRS.getPits().get(13).getStones()
                    ? " Wins Player1 with " + boardRS.getPits().get(6).getStones() + " stones!!!"
                    : " Wins Player2 with " + boardRS.getPits().get(13).getStones() + " stones!!!"));
            boardRS.setNextTurnPlayer(-1);
            boardRS.setGameFinished(true);
        }

        int stonesP2 = 0;
        for (int i = Board.K1_INDEX + 1; i < Board.PITS_COUNT; i++) {
            stonesP2 += boardRS.getPits().get(i).getStones();
        }
        if (stonesP2 == 0) {
            for (int i = 0; i < Board.K1_INDEX - 1; i++) {
                boardRS.getPits().get(i).emptyPit();
            }
            boardRS.getPits().get(Board.K1_INDEX).addCaptureStones(stonesP1);
            boardRS.setMessage(boardRS.getMessage()
                    +" Game ends! Player2 has no remaining stones!."
                    + (boardRS.getPits().get(6).getStones() > boardRS.getPits().get(13).getStones()
                    ? " Wins Player1 with " + boardRS.getPits().get(6).getStones() + " stones!!!"
                    : " Wins Player2 with " + boardRS.getPits().get(13).getStones() + " stones!!!"));
            boardRS.setNextTurnPlayer(-1);
            boardRS.setGameFinished(true);
        }
        return boardRS;
    }

}
