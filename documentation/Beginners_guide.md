# Beginner's guide for minesweeper bot setup

## Java JDK 11 and JavaFX 11

In order to develop and build the minesweeper, you will need at least version Java JDK 11 installed in your computer. Note that Java JDK 11 does not contain JavaFX 11 anymore, so make sure you have that too. JavaFX 11 can be downloaded from e.g. [here](https://gluonhq.com/products/javafx/).

If your ./gradlew commands do not find your Java 11 JDK, even if you have it installed, you may get some response as follows:

```$ ./gradlew build```

```./gradlew: 188: exec: /usr/lib/jvm/java-11-openjdk/bin/java: not found```

You can do as follows:

```$ export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/```

It is best to add that line into your .bashrc file:

If you prefer using Netbeans IDE, you will need at least version Netbeans 11. You can install it from e.g. [here](https://computingforgeeks.com/install-netbeans-ide-on-debian-ubuntu-and-linux-mint/).

Just follow the steps 1-4 in that guideline.

Make sure that you have all the necessary plugins installed. Among others, you will need at least the following plugins: Groovy (contains gradle) and JavaFX 2.
