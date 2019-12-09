# Beginner's guide for minesweeper bot setup

## Java JDK 11 and JavaFX 11

In order to develop and build the minesweeper, you will need at least version Java JDK 11 installed in your computer. Unlike earlier versions of Java, Java JDK 11 does not contain JavaFX anymore, so make sure you have that too. JavaFX 11 can be downloaded from e.g. [here](https://gluonhq.com/products/javafx/).

We recommend using **OpenJDK 11**.

If your ./gradlew commands do not find your Java 11 JDK, even if you have it installed, you may get some response as follows:

```$ ./gradlew build```

```./gradlew: 188: exec: /usr/lib/jvm/java-11-openjdk/bin/java: not found```

In that case, you can check which version of openjdk11 you have. If you have e.g. java-11-openjdk-amd64, you can give the following command:

```$ export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/```

If you needed that, it is then best to add that line into your .bashrc file.

If you prefer using Netbeans IDE, you will need at least version Netbeans 11. You can install it from e.g. [here](https://computingforgeeks.com/install-netbeans-ide-on-debian-ubuntu-and-linux-mint/).

Just follow the steps 1-4 in that guideline.

Make sure that you have all the necessary plugins installed. Among others, you will need at least the following plugins in Netbeans 11: Groovy (contains gradle) and JavaFX 2.

## Gradle version 6

This project was originally initiated with Gradle 5.6.2.
In November 2019, Gradle has been updated to version 6.0.1. It has support for openjdk13, too.
When Gradle version 6.0 was introduced, Travis Continuous Integration tool had some issue with openjdk11. Travis CI started working when .travis.yml was updated to use openjdk13. You can still use openjdk11 in your own machine.

## Minesweeper

This Minesweeper App follows the basic functionalities of the "original Minesweeper".
Its graphical user interface has been slightly modified. For example, the squares are larger and darker than in the original one. Some terms may not be familiar:

### Flagging

The location of a suspected mine can be marked by flagging it with the right mouse button. Note: The only good reason to flag is to clear more squares by chording.

### Chording

When an uncovered square with a number has exactly the correct number of adjacent squares flagged, performing a click with both mouse buttons on it will uncover all unmarked squares near it. This is called a Chord.

### Pre-set seed

Minesweeper uses random numbers to generate the minefield. With "Use a pre-set seed" button it is possible to pre-set the seed for the random generator and get exactly the same minefield every time. However, as the minefield can only be generated after the first click on the board, the location of which which may vary, the minefield will be the same only if the same square is clicked in the beginning.

### Custom board

The maximum size of the minesweeper's custom sized board is set to fit on the "fuxi-laptop" screen. As the first click always opens a 3x3 area with no mines on it, the maximum number of mines on the custom board is limited to (height * width - 9).

## Other documents to study

[Architecture Documentation](https://github.com/TiraLabra/minesweeper/blob/master/documentation/Architecture-Documentation.md)

[Writing Bots](https://github.com/TiraLabra/minesweeper/blob/master/documentation/Writing-Bots.md)

[Testing headlessly](https://github.com/TiraLabra/minesweeper/blob/master/documentation/Writing-Bots.md)