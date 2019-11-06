# Writing Minesweeper bots

This document describes the required Java interfaces and the general functionality
which must be implemented in order to write bots for this Minesweeper implementation.
It also describes what data structures are made available to the bot and how the
bot can interac t with those data structures.

Currently the bot is called using the "Help (Bot)" button in the GUI when the game is running.

## Bot interface (Bot.java)

At the core of the bot framework for this Minesweeper implementation is the Bot interface,
which any bots must implement.

The interface defines a single method 
```java
public boolean makeMove(Board board)
```
which is used by the Minesweeper to relay information to the bot and ask the bot to make
a decision. As a parameter, the current state of the board is given in the form of a
Board object. The return value represents whether the Bot opened a mine of not.

In the bot implementations, this function must be overridden with your own custom
functionality that represents a single round being played. The bot can take actions
by directly modifying the state of the Board object using its methods.

## Board class

The Board class is the data type that is used as the internal representation of the
board state within the application. It is based on a two dimensional array containing
all of the individual squares as Square objects.

The primary interaction with the board happens via the ```public Square getSquareAt(int x, int y)```
and ```public boolean open(int x, int y)``` methods. These allow you to read the state of a square
at a given coordinate and opening a square at a given coordinate. There is also a method
for chording called ```public boolean chordedOpen(int x, int y)```. You can read about
chording [here](http://www.minesweeper.info/wiki/Chord).

## Square class

A Square object provides access to the state of an individual Square on the board.
It has methods for querying whether a Square is a mine, how many adjacent mines it has
and allows flagging/unflagging the square.

**NOTE:** A Square must be opened before you can check if it's a mine or how many adjacent
mines it has. Attempting to read the full state of a closed Square will result in an
AssertionError.
