# java-kalah
Paying around with Java and a Kalah(6,6) game.

The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah. 

## Basics

- 6 Pits per player
- 6 Stones by pit
- 1 Kalah per player
- 2 Players

## Board

| P2 |    |    |    |    |    |   | P1 |
|----|----|----|----|----|----|---|----|
|    | 12 | 11 | 10 | 9  | 8  | 7 |    |
| 13 |    |    |    |    |    |   |  6 |
|    | 0  |  1 |  2 |  3 |  4 | 5 |    |

Player 1 Kalah's is position 6, his pits are 0-5

Player 2 Kalah's is position 13, his pits are 7-12

## How to play

- Always move counter-clockwise
- Start your turn only in your pits
- Always take all your selected pit's stones and distribute each one in the adyecent pits including your Kalah


## Rules

- If your last stone ends in your Kalah you get another turn
- If your last stone ends in an empty pit and your oposite pit had stones, you take his pit stones and yours to your Kalah 
- If a player had all his pits empty the oposite player take the remaining stones to his Kalah and the game ends
- At the game endings you count all your Kalah stones and the player with more stones wins

## Classes 

### Pit
Stones [int] Count of current stones in this pit 

Oposite [int] Index of its oposite pit

IsKalah [boolean] Validates if this pit is a Kalah box

Owner [int] If this pit belongs to Player 1 value:1, else value:2

## Rest Services

- GET /game-status : Board Status
- POST /play/id-player?pit=# : Player play, setting the initial pit where his turn starts
- GET /logs : Players game logs 


