package org.psnbtech;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class BoardPanelTest {

    private BoardPanel boardPanel;
    private Graphics graphics;
    private SnakeGame snakeGame;

    @Before
    public void setUp() {
        // instance of SnakeGame using reflection
        try {
            Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            snakeGame = constructor.newInstance();
            
            //LinkedList for directions (N,S,E,W)
            
            snakeGame.directions = new LinkedList<>();
            snakeGame.directions.add(Direction.North);
            snakeGame.directions.add(Direction.South);
            snakeGame.directions.add(Direction.East);
            snakeGame.directions.add(Direction.West);
            
            boardPanel = new BoardPanel(snakeGame); // instantiate BoardPanel
            BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);// BufferedImage to obtain its graphics context
            graphics = bufferedImage.getGraphics();
        } catch (NoSuchMethodException | SecurityException | InstantiationException |
                 IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // test case for clearing the board
    @Test
    public void testClearBoard() {
        boardPanel.setTile(0, 0, TileType.SnakeHead);
        boardPanel.setTile(1, 1, TileType.Fruit);

        boardPanel.clearBoard(); // clear the board

        // check if all tiles are null
        for (int x = 0; x < BoardPanel.COL_COUNT; x++) {
            for (int y = 0; y < BoardPanel.ROW_COUNT; y++) {
                assertNull(boardPanel.getTile(x, y));
            }
        }
    }

    // test case for setting and getting a tile
    @Test
    public void testSetAndGetTile() {
        // set a tile
        boardPanel.setTile(2, 3, TileType.SnakeBody);

        // check if the tile is set correctly
        assertEquals(TileType.SnakeBody, boardPanel.getTile(2, 3));
    }

    // test case for painting the board
    @Test
    public void testPaintComponent() {
        // set background color for testing
        boardPanel.setBackground(Color.WHITE);
        boardPanel.paintComponent(graphics);
    }

    // test case for drawing a fruit tile
    @Test
    public void testDrawTile_Fruit() {
        // set a fruit tile
        boardPanel.setTile(0, 0, TileType.Fruit);
        // call paintComponent to draw the tile
        boardPanel.paintComponent(graphics);
    }

    // test case for drawing a snake body tile
    @Test
    public void testDrawTile_SnakeBody() {
        // set a snake body tile
        boardPanel.setTile(0, 0, TileType.SnakeBody);
        // draw the tile
        boardPanel.paintComponent(graphics);
    }

    @Test
    public void testDrawTile_SnakeHead() {
        // drawing the snake head in all four directions
        for (Direction direction : Direction.values()) {
        	
            // clear previous directions and set the new direction
            snakeGame.directions.clear();
            snakeGame.directions.add(direction);
            
            boardPanel.setTile(0, 0, TileType.SnakeHead);
            boardPanel.paintComponent(graphics);
        }
    }
    
    
    //paintComponent
    
    private void simulateGameState(boolean isNewGame, boolean isGameOver, boolean isPaused) {
        try {
        	
            Field newGameField = SnakeGame.class.getDeclaredField("isNewGame");
            Field gameOverField = SnakeGame.class.getDeclaredField("isGameOver");
            Field pausedField = SnakeGame.class.getDeclaredField("isPaused");

            newGameField.setAccessible(true);
            gameOverField.setAccessible(true);
            pausedField.setAccessible(true);

            
            newGameField.set(snakeGame, isNewGame);
            gameOverField.set(snakeGame, isGameOver);
            pausedField.set(snakeGame, isPaused);

            
            boardPanel.paintComponent(graphics);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set game state due to reflection error: " + e.getMessage());
        }
    }

    

    @Test
    public void testPaintComponent_NewGame() {
        simulateGameState(true, false, false);
    }

    @Test
    public void testPaintComponent_GameOver() {
        simulateGameState(false, true, false);
    }

    @Test
    public void testPaintComponent_Paused() {
        simulateGameState(false, false, true);
    }
    
    
    //setTile
    @Test
    public void setTileTest() {
        
        Point point = new Point(3, 4); 
        TileType expectedType = TileType.SnakeBody;

        boardPanel.setTile(point, expectedType); 

     
        assertEquals("Tile at point should be set correctly",
                     expectedType, boardPanel.getTile(point.x, point.y));
    }

}
