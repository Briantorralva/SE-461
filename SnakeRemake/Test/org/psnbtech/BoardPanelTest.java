package org.psnbtech;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

public class BoardPanelTest {

    private BoardPanel boardPanel;
    private Graphics graphics;

    @Before
    public void setUp() {
        // instance of SnakeGame using reflection
        try {
            Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            SnakeGame snakeGame = constructor.newInstance();
            boardPanel = new BoardPanel(snakeGame); // instantiate BoardPanel
            BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);// BufferedImage to obtain its graphics context
            graphics = bufferedImage.getGraphics();
        } catch (NoSuchMethodException | SecurityException | InstantiationException |
                 IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // Test case for clearing the board
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

    // Test case for setting and getting a tile
    @Test
    public void testSetAndGetTile() {
        // set a tile
        boardPanel.setTile(2, 3, TileType.SnakeBody);

        // check if the tile is set correctly
        assertEquals(TileType.SnakeBody, boardPanel.getTile(2, 3));
    }

    // Test case for painting the board
    @Test
    public void testPaintComponent() {
        // set background color for testing
        boardPanel.setBackground(Color.WHITE);
        boardPanel.paintComponent(graphics);
    }

    // Test case for drawing a fruit tile
    @Test
    public void testDrawTile_Fruit() {
        // set a fruit tile
        boardPanel.setTile(0, 0, TileType.Fruit);
        // call paintComponent to draw the tile
        boardPanel.paintComponent(graphics);
    }

    // Test case for drawing a snake body tile
    @Test
    public void testDrawTile_SnakeBody() {
        // set a snake body tile
        boardPanel.setTile(0, 0, TileType.SnakeBody);
        // call paintComponent to draw the tile
        boardPanel.paintComponent(graphics);
    }

    
}
