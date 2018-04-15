# java-kalah
Paying around with Java and a Kalah(6,6) game

The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah. 

## Basics

- 6 Pits per player
- 6 Stones by pit
- 1 Kalah per player
- 2 Players
- Always move counter-clockwise
- Start your turn only in your pits
- Always take all your selected pit's stones and distribute each one in the pits including your Kalah

## Board

| P2 |    |    |    |    |    |   | P1 |
|----|----|----|----|----|----|---|----|
|    | 12 | 11 | 10 | 9  | 8  | 7 |    |
| 13 |    |    |    |    |    |   |  6 |
|    | 0  |  1 |  2 |  3 |  4 | 5 |    |

Player 1 Kalah's is position 6, his pits are 0-5

Player 2 Kalah's is position 13, his pits are 7-12

## Rules

- If your last stone ends in your Kalah you get another turn
- If your last stone ends in an empty pit and your oposite pit had stones, you take his pit stones and yours to your Kalah 
- If a player had all his pits empty the oposite player take the remaining stones to his Kalah and the game ends
- At the game endings you count all your Kalah stones and the player with more stones wins




