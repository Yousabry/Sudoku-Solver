# Sudoku-Solver
Java program that can solve all sudokus (including ones that require trial and error).

Sudoku.java:
Tries deducing solutions using the methods in solver(). If that is not successful, guesses a value of a tile, with three possible outcomes:
  - Puzzle can now be solved. Great, end of program.
  - Puzzle now has an inconsistency. The original possibility guessed is certainly false.
  - Solver gets stuck again. Worst case, no information has been gained (could still be correct or incorrect guess).

This combination of sure deductions and guesses (with backtracking when necessary), has been able to solve every puzzle difficulty.

Things to work on:
  - Better method of receiving starting values of the puzzle (ex. https://www.sudoku-solutions.com/)
  - Clean up logic to not need certainty for every tile (can insteaf save a snapshot of the puzzle every time guessing is necessary)
