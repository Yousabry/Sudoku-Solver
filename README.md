# Sudoku-Solver
Java program that can solve basic sudokus (ones that dont require guessing).

Tile.java:
Every tile has an id, value, row, column, poss, tempposs and certainty.

poss - boolean[] length 9, each index represents if the val+1 is a possibility in the given tile
tempposs - when trial and error is integrated, will use this array to store the backup poss info
certainty - when trial and error is integrated, will mark the certainty as false, to be able to backtrack

Square.java & Line.java:
Square and line have the same attributes: id and tiles

tiles - contains all the tiles in the given line/square

Sudoku.java:
All Sudokus have a puzzle id, puzzle name, alltiles, alllines, allsquares

alltiles, alllines, allsquares - lists of the appropriate objects in the Sudoku

isValid() - returns boolean based on validity of puzzle
updatePoss() - updates the poss attribute for all unsolved tiles without resetting previous changes
(if an unusual deduction was made earlier setting a possibility to false, update poss will not lose that information)
solver() - runs each solving technique seperately (expand the programs ability to solve complex puzzles by adding another solving method)
s1(), s2(), s3() - check if only one possibility for a tile, line or square respectively
compress() - returns String of tile id followed by value, seperated by commas (ex. 23,336 --> tile 2 has value 3 and tile 33 has value 6)

Things to work on:
- Better method of receiving starting values of the puzzle (maybe something involving pictures or parsing)
- Integrate trial and error to take effect when algorithm is stuck (usually gets stuck on evil puzzles)
