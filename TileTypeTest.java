package org.psnbtech;

import org.junit.Test;
import static org.junit.Assert.*;

public class TileTypeTest {

    @Test
    public void testTileTypeConstants() {
        // checks if the constants are correctly defined
        assertEquals("Fruit", TileType.Fruit.name());
        assertEquals("SnakeHead", TileType.SnakeHead.name());
        assertEquals("SnakeBody", TileType.SnakeBody.name());

        assertNotEquals(TileType.Fruit, TileType.SnakeHead);
        assertNotEquals(TileType.Fruit, TileType.SnakeBody);
        assertNotEquals(TileType.SnakeHead, TileType.SnakeBody);
    }

    @Test
    public void testTileTypeToString() {
      
        assertEquals("Fruit", TileType.Fruit.toString());
        assertEquals("SnakeHead", TileType.SnakeHead.toString());
        assertEquals("SnakeBody", TileType.SnakeBody.toString());
    }
}
