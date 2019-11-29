# Instructions for testing your bot without GUI

You can write your test with the test class and run it with gradle task ``headlessTest``.  

To run a single game you just need place your bot the classes ``bot`` variable and a new StartSelectView and create a new TestApp class in the main method.  
The constructor creates and runs the with your bot as a player game. After that you can access the board and stats/moves with ``gameStats`` and ``board`` variables.  
  
The class currently has some example code. It runs 100 10x16 games with 50 mines. After that it prints the gamestatus, board and moves(with stats) to text.txt file.
```java
        //Values saved as Pairs, this is needed to access the values of both board and gamestats.
        ArrayList<Pair<GameStats,Board>> stats = new ArrayList<>();
        //Play 100 games and save the stats and board to array
        for(int i = 0; i < 100; i++) {
        TestApp app = new TestApp(new Random().nextLong(),10,16,50);
        stats.add(new Pair<GameStats,Board>(app.gameStats, app.board));
        }
        //Sets the out stream to file test.txt in root of project.
        try {
        System.setOut(new PrintStream(new File("test.txt")));
        } catch (Exception e){}
        //Print the stats, board and game ending status to file.
        stats.stream().forEach(s -> {
            System.out.println("---------------------");
            System.out.println("Game: " + s.getValue().gameWon);
            System.out.println(s.getValue());
            s.getKey().moves.stream().forEach(k -> System.out.println(k + " at (" + k.x + "," + k.y + ")"));
        });
```

The print for this example code looks like this:
```
Game: false  
Field   
XXXXXXXXXXXXXXXX
XXXXXXXXXXXX*XXX
XXXXXXXXXXXXXXXX
12XXXXXXXXXXXXXX
012XXXXXXXXXXXXX
002XXXXXXXXXXXXX
113XXXXXXXXXXXXX
XXXXXXXXXXXXXXXX
112223XXXXXXXXXX
000002XXXXXXXXXX

Move: OPEN Distance: 14.42 Time: 0.00 at (1,12)
Move: OPEN Distance: 11.18 Time: 0.00 at (9,0)
Move: HIGHLIGHT Distance: 8.54 Time: 0.00 at (7,11)
Move: HIGHLIGHT Distance: 3.16 Time: 0.00 at (4,3)
Move: OPEN Distance: 3.16 Time: 0.00 at (5,0)
Move: HIGHLIGHT Distance: 0.00 Time: 0.00 at (6,3)
```
