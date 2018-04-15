# java-kalah
Paying around with Java and a Kalah(6,6) game.

The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah. 

## Basics

- 6 Pits per player
- 6 Stones by pit
- 1 Kalah per player
- 2 Players

## Board

| P2 |   |   |   |   |   |   | P1 |
|----|---|---|---|---|---|---|----|
|    | L | K | J | I | H | G |    |
| K2 |   |   |   |   |   |   | K1 |
|    | A | B | C | D | E | F |    |

Player 1 Kalah's is position K1, his pits are A-F

Player 2 Kalah's is position K2, his pits are G-L

## How to play

- Always move counter-clockwise
- Start your log only in your pits
- Always take all your selected pit's stones and distribute each one in the adyecent pits including your Kalah


## Rules

- If your last stone ends in your Kalah you get another log
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

#### Board Status
GET [/v1/game/{game-id}/board-status?in-html=true] 

#### Players log
GET [/v1/game/{game-id}/play/{player-id}?pit-id={pit-id}&in-html=true] 

#### Inital board layout
GET [/v1/game/initial-board?in-html=true]


