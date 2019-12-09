
package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import minesweeper.generator.MinefieldGenerator;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class BoardTest {
    private Board board;
    private MinefieldGenerator generator;

    @Before
    public void setUp() {
        generator = new MinefieldGenerator();
        board = new Board(generator, 10, 10, 3);
        board.firstMove = false;
    } 

    @After
    public void tearDown() {
    // empty method
    }

    @Test
    public void boardInitializesAllSquares() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                assertTrue(board.board[y][x] != null);
            }
        }
    }

    @Test
    public void boardIsInitializedWithCorrectWidthAndHeight() {
        board = new Board(generator, 10, 10, 3);

        assertEquals(10, board.height);
        assertEquals(10, board.width);
    }

    @Test
    public void withinBoardReturnsTrueForWithinBoard() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                assertTrue(board.withinBoard(x, y));
            }
        }
    }

    @Test
    public void withinBoardReturnsFalseForOutsideBoard() {
        assertEquals(false, board.withinBoard(20, 5));
        assertEquals(false, board.withinBoard(5, 20));
        assertEquals(false, board.withinBoard(-5, 5));
        assertEquals(false, board.withinBoard(5, -5));
    }

    @Test
    public void addingAMineWorks() {
        Square square = new Square(5, 5);
        square.setMine();

        board.addSquare(square, 5, 5);
        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);

        assertTrue(board.board[5][5].isMine());
    }

    @Test
    public void clickinOnAMineReturnsFalse() {
        Square square = new Square(5, 5);
        square.setMine();

        board.addSquare(square, 5, 5);

        assertEquals(false,  board.makeMove(new Move(MoveType.OPEN, 5, 5)));
    }

    @Test
    public void clickingOnAnEmptySquareReturnsTrue() {
        Square square = new Square(5, 5);
        square.setMine();

        board.addSquare(square, 5, 5);
        
        Move move = new Move(MoveType.OPEN, 2, 2);
        assertEquals(true, board.makeMove(move));
    }

    @Test
    public void openingEmptyFieldOpensAllSquares() {
        
        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                assertTrue(board.board[y][x].isOpened());
            }
        }
    }

    @Test
    public void openingASquareDoesNotOpenMines() {
        Square square = new Square(2, 2);
        square.setMine();

        board.addSquare(square, 2, 2);
        board.incrementAdjacentSquares(2,2);

        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);

        assertEquals(false, board.board[2][2].isOpened());
    }

    @Test
    public void openingASquareDoesNotOpenFlagged() {
        board.board[4][5].toggleFlagged();
        
        assertEquals(true, board.board[4][5].isFlagged());

        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);

        assertEquals(false, board.board[4][5].isOpened());
    }

    @Test
    public void flaggedSquareReturnsTrueWhenOpened() {
        board.board[5][5].toggleFlagged();
        Move move = new Move(MoveType.OPEN, 5, 5);
        assertTrue(board.makeMove(move));
    }
    
    @Test
    public void openingASquareAddsToOpenedSquares() {
        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);

        assertTrue(board.getOpenSquares().contains(board.getSquareAt(5, 5)));
    }
    
    @Test
    public void chordedOpenWorksWhenNoFlagged() {
        board.board[0][2].setMine();
        board.incrementAdjacentSquares(0, 2);

        board.board[1][2].setMine();
        board.incrementAdjacentSquares(1, 2);

        board.board[2][2].setMine();
        board.incrementAdjacentSquares(2, 2);
        
        Move move = new Move(MoveType.OPEN, 0, 0);
        board.makeMove(move);
        
        Move chordedMove = new Move(MoveType.CHORD, 0, 0);
        board.makeMove(chordedMove);

        assertEquals(true, board.board[1][0].isOpened());
    }
    
    @Test
    public void chordedOpenDoesNotOpenFlagged() {
        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[4][4].setMine();
        board.board[4][4].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[6][5].setMine();
        board.board[6][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        
        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);

        
        Move chordedMove = new Move(MoveType.CHORD, 5, 5);
        board.makeMove(chordedMove);

        assertEquals(false, board.board[4][5].isOpened());
        assertEquals(false, board.board[4][4].isOpened());
        assertEquals(false, board.board[6][5].isOpened());
    }

    @Test
    public void chordedOpenOpensUnflagged() {
        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[4][4].setMine();
        board.board[4][4].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[6][5].setMine();
        board.board[6][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        
        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);


        Move chordedMove = new Move(MoveType.CHORD, 5, 5);
        board.makeMove(chordedMove);

        for (int xInc = -1; xInc <= 1; xInc++) {
            for (int yInc = -1; yInc <= 1; yInc++) {
                if (!board.board[5 + xInc][5 + yInc].isFlagged()) {
                    assertEquals(true, board.board[5 + xInc][5 + yInc].isOpened());
                }
            }
        }
    }

    @Test
    public void chordedOpenReturnsFalseForMineHit() {
        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[4][4].setMine();
        board.incrementAdjacentSquares(4, 5);

        board.board[6][6].toggleFlagged();

        board.board[6][5].setMine();
        board.board[6][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[5][5].open();
        assertEquals(false, board.makeMove(new Move(MoveType.CHORD, 5, 5)));
    }

    @Test
    public void chordedOpenWillNotRunIfSurroundingMinesAndFlagsDoNotMatch() {
        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[4][4].setMine();
        board.incrementAdjacentSquares(4, 5);

        board.board[6][5].setMine();
        board.board[6][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);


        Move chordedMove = new Move(MoveType.CHORD, 5, 5);
        assertEquals(true, board.makeMove(chordedMove));

        assertEquals(false, board.board[6][6].isOpened());
    }

    @Test
    public void boardHighlightsCanBeCleared() {
        board.board[5][5].highlight = Highlight.RED;

        board.clearHighlights();

        for (int y = 0; y < board.height; y++) {
            for (int x = 0; x < board.width; x++) {
                assertEquals(Highlight.NONE, board.board[x][y].highlight);
            }
        }
    }

    @Test
    public void makingOpenMoveOpensSquare() {
        board.makeMove(new Move(MoveType.OPEN, 5, 5));

        assertTrue(board.getSquareAt(5, 5).isOpened());
    }

    @Test
    public void makingChordMoveOpensSquares() {
        board.firstMove = false;

        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);
        
        Move move = new Move(MoveType.OPEN, 5, 5);
        board.makeMove(move);
        
        board.makeMove(new Move(MoveType.CHORD, 5, 5));

        for (int xInc = -1; xInc <= 1; xInc++) {
            for (int yInc = -1; yInc <= 1; yInc++) {
                if (!board.board[5 + xInc][5 + yInc].isFlagged()) {
                    assertEquals(true, board.board[5 + xInc][5 + yInc].isOpened());
                }
            }
        }
    }

    @Test
    public void makingFlagMoveFlagsSquare() {
        board.makeMove(new Move(MoveType.FLAG, 5, 5));

        assertTrue(board.getSquareAt(5, 5).isFlagged());
    }

    @Test
    public void makingHighlightMoveHighlightsSquare() {
        board.makeMove(new Move(5, 5, Highlight.RED));

        assertEquals(Highlight.RED, board.getSquareAt(5, 5).highlight);
    }

    @Test
    public void observedBoardSendsNeededCallbacks(){
        this.board = new Board(generator, 2, 2, 0);
        Square[] neededSquares = new Square[]{this.board.board[0][0], this.board.board[0][1], this.board.board[1][0]};
        ArrayList<Square> gotSquares = new ArrayList<Square>();
        Function<Square,Void> callback = new Function<Square,Void>() {
            @Override
            public Void apply(Square t) {
                gotSquares.add(t);
                return null;
            }
        };
        board.setChangeObserver(callback);
       
        Move move = new Move(MoveType.OPEN, 1, 1);
        board.makeMove(move);
        assertTrue(gotSquares.containsAll(Arrays.asList(neededSquares)));
    }

    @Test
    public void getUnflaggedMinesRight() {

        assertTrue(board.getUnflaggedMines() == 3);
        Move move = new Move(MoveType.FLAG, 1, 1);
        board.makeMove(move);
        assertTrue(board.getUnflaggedMines() == 2);
        move = new Move(MoveType.FLAG, 1, 1);
        board.makeMove(move);
        assertTrue(board.getUnflaggedMines() == 3);
    }

    @Test
    public void survivesXYNotWithinBoardInBFS() {
        board.runBFS(10,10);
        assert (true);
    }
 
    @Test
    public void toStringWorksToo() {
        String basicOutput = "Field \n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n" +
            "XXXXXXXXXX\n";
        
          //  "    ";
        assertEquals(basicOutput, board.toString());
    }
    
}
