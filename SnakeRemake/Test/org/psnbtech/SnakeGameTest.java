package org.psnbtech;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.Random; 

public class SnakeGameTest {

    @Test
    public void testSpawnFruit() throws Exception {
        try {
            // get the constructor for SnakeGame and make it accessible
            Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // create an instance of SnakeGame using the constructor
            SnakeGame snakeGame = constructor.newInstance();

            // initialize the random field
            Field randomField = SnakeGame.class.getDeclaredField("random");
            randomField.setAccessible(true);
            randomField.set(snakeGame, new Random()); // initialize random

            // initialize the snake field
            Field snakeField = SnakeGame.class.getDeclaredField("snake");
            snakeField.setAccessible(true);
            snakeField.set(snakeGame, new LinkedList<>()); 

            // get the spawnFruit method using reflection and make it accessible
            Method spawnFruitMethod = SnakeGame.class.getDeclaredMethod("spawnFruit");
            spawnFruitMethod.setAccessible(true);

            // call the spawnFruit method
            spawnFruitMethod.invoke(snakeGame);

            // get the board field using reflection
            Field boardField = SnakeGame.class.getDeclaredField("board");
            boardField.setAccessible(true);
            BoardPanel board = (BoardPanel) boardField.get(snakeGame);

            int fruitCount = 0;
            Set<Integer> occupiedIndices = new HashSet<>();

            for (int x = 0; x < BoardPanel.COL_COUNT; x++) {
                for (int y = 0; y < BoardPanel.ROW_COUNT; y++) {
                    TileType type = board.getTile(x, y);
                    if (type == TileType.Fruit) {
                        fruitCount++;
                        // check if the fruit is within the board boundaries
                        assertTrue(x >= 0 && x < BoardPanel.COL_COUNT);
                        assertTrue(y >= 0 && y < BoardPanel.ROW_COUNT);
                        // check if the fruit is not occupying the same index as any other fruit
                        int index = y * BoardPanel.COL_COUNT + x;
                        assertTrue(!occupiedIndices.contains(index));
                        occupiedIndices.add(index);
                    }
                }
            }

            // check if only one fruit is spawned
            assertEquals(1, fruitCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
