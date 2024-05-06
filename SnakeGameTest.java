package org.psnbtech;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.Random; 

public class SnakeGameTest {
	
	private SnakeGame snakeGame;
    private Robot robot;

    @Before
    public void setUp() throws AWTException {
        snakeGame = new SnakeGame();
        robot = new Robot();
        snakeGame.snake = new LinkedList<>();
        snakeGame.directions = new LinkedList<>();
        snakeGame.logicTimer = new Clock(9.0f); 
        snakeGame.random = new Random(); 
    }
	
	
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
    
    @Test
    public void testUpdateSnake() throws Exception {
        try {
            // get the constructor for SnakeGame and make it accessible
            Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // create an instance of SnakeGame using the constructor
            SnakeGame snakeGame = constructor.newInstance();

            // initialize the snake field
            Field snakeField = SnakeGame.class.getDeclaredField("snake");
            snakeField.setAccessible(true);
            LinkedList<Point> snake = new LinkedList<>();
            snake.add(new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2));
            snakeField.set(snakeGame, snake);

            // initialize the directions field
            Field directionsField = SnakeGame.class.getDeclaredField("directions");
            directionsField.setAccessible(true);
            LinkedList<Direction> directions = new LinkedList<>();
            directions.add(Direction.North); // or any valid initial direction
            
            directionsField.set(snakeGame, directions);

            // get the updateSnake method using reflection and make it accessible
            Method updateSnakeMethod = SnakeGame.class.getDeclaredMethod("updateSnake");
            updateSnakeMethod.setAccessible(true);

            // call the updateSnake method
            TileType result = (TileType) updateSnakeMethod.invoke(snakeGame);

            // check if the snake head is correctly updated
            Point expectedHead = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2 - 1);
            assertEquals(expectedHead, snake.get(0));

            // check if the result is as expected (no collision)
            assertEquals(null, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdateSnakeOutOfBounds() throws Exception {
        try {
            // get the constructor for SnakeGame and make it accessible
            Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // create an instance of SnakeGame using the constructor
            SnakeGame snakeGame = constructor.newInstance();

            // initialize the snake field
            Field snakeField = SnakeGame.class.getDeclaredField("snake");
            snakeField.setAccessible(true);
            LinkedList<Point> snake = new LinkedList<>();
            snake.add(new Point(0, 0)); // snake head at top-left corner
            snakeField.set(snakeGame, snake);

            // initialize the directions field
            Field directionsField = SnakeGame.class.getDeclaredField("directions");
            directionsField.setAccessible(true);
            LinkedList<Direction> directions = new LinkedList<>();
            directions.add(Direction.West); // move west, out of bounds
            directionsField.set(snakeGame, directions);

            // get the updateSnake method using reflection and make it accessible
            Method updateSnakeMethod = SnakeGame.class.getDeclaredMethod("updateSnake");
            updateSnakeMethod.setAccessible(true);

            // call the updateSnake method
            TileType result = (TileType) updateSnakeMethod.invoke(snakeGame);

            // snake should collide with itself
            assertEquals(TileType.SnakeBody, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testStartGame() throws Exception {
        try {
            // get the constructor for SnakeGame and make it accessible
            Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // create an instance of SnakeGame using the constructor
            SnakeGame snakeGame = constructor.newInstance();

            // initialize the fields required for startGame method
            Field randomField = SnakeGame.class.getDeclaredField("random");
            randomField.setAccessible(true);
            Field snakeField = SnakeGame.class.getDeclaredField("snake");
            snakeField.setAccessible(true);
            Field directionsField = SnakeGame.class.getDeclaredField("directions");
            directionsField.setAccessible(true);
            Field logicTimerField = SnakeGame.class.getDeclaredField("logicTimer");
            logicTimerField.setAccessible(true);
            Field isNewGameField = SnakeGame.class.getDeclaredField("isNewGame");
            isNewGameField.setAccessible(true);

            new Thread(() -> {
                try {
                    Method startGameMethod = SnakeGame.class.getDeclaredMethod("startGame");
                    startGameMethod.setAccessible(true);
                    startGameMethod.invoke(snakeGame);
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception occurred while invoking startGame: " + e.getMessage());
                }
            }).start();

            // create Robot to simulate user input
            Robot robot = new Robot();

            // simulate key press to start the game
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            // check if all the fields are initialized correctly
            assertNotNull(randomField.get(snakeGame));
            assertNotNull(snakeField.get(snakeGame));
            assertNotNull(directionsField.get(snakeGame));
            assertNotNull(logicTimerField.get(snakeGame));
            assertTrue((boolean) isNewGameField.get(snakeGame));

            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }
    
    
    @Test
    public void testUpdateGame() {
      
        snakeGame.resetGame();
        snakeGame.spawnFruit();
        int initialScore = snakeGame.getScore();
        int initialFruitsEaten = snakeGame.getFruitsEaten();
        int initialNextFruitScore = snakeGame.getNextFruitScore();
        Direction initialDirection = snakeGame.getDirection();

        // simulate a game update
        snakeGame.updateGame();

        // checks if the score increased by the correct amount
        assertEquals(initialScore + initialNextFruitScore, snakeGame.getScore());

        // check if fruits eaten increased
        assertEquals(initialFruitsEaten + 1, snakeGame.getFruitsEaten());

        // check if the next fruit score decreased 
        if (initialNextFruitScore > 10) {
            assertEquals(initialNextFruitScore - 1, snakeGame.getNextFruitScore());
        }

        // check if the direction remained the same
        assertEquals(initialDirection, snakeGame.getDirection());
    }
    
    
    @Test
    public void testUpdateSnakeSouth() throws Exception {
        try {
           
            Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            SnakeGame snakeGame = constructor.newInstance();

            Field snakeField = SnakeGame.class.getDeclaredField("snake");
            snakeField.setAccessible(true);
            LinkedList<Point> snake = new LinkedList<>();
            snake.add(new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2));
            snakeField.set(snakeGame, snake);

            Field directionsField = SnakeGame.class.getDeclaredField("directions");
            directionsField.setAccessible(true);
            LinkedList<Direction> directions = new LinkedList<>();
            directions.add(Direction.South); // Move south
            directionsField.set(snakeGame, directions);

            Method updateSnakeMethod = SnakeGame.class.getDeclaredMethod("updateSnake");
            updateSnakeMethod.setAccessible(true);

            TileType result = (TileType) updateSnakeMethod.invoke(snakeGame);

            // check if the snake head is correctly updated
            Point expectedHead = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2 + 1); // move south
            assertEquals(expectedHead, snake.get(0));

            // check if the result is as expected (no collision)
            assertEquals(null, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateSnakeEast() throws Exception {
        try {
            
            Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            SnakeGame snakeGame = constructor.newInstance();

            Field snakeField = SnakeGame.class.getDeclaredField("snake");
            snakeField.setAccessible(true);
            LinkedList<Point> snake = new LinkedList<>();
            snake.add(new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2));
            snakeField.set(snakeGame, snake);

            Field directionsField = SnakeGame.class.getDeclaredField("directions");
            directionsField.setAccessible(true);
            LinkedList<Direction> directions = new LinkedList<>();
            directions.add(Direction.East); // Move east
            directionsField.set(snakeGame, directions);

            Method updateSnakeMethod = SnakeGame.class.getDeclaredMethod("updateSnake");
            updateSnakeMethod.setAccessible(true);

            // call the updateSnake method
            TileType result = (TileType) updateSnakeMethod.invoke(snakeGame);

            // check if the snake head is correctly updated
            Point expectedHead = new Point(BoardPanel.COL_COUNT / 2 + 1, BoardPanel.ROW_COUNT / 2); // move east
            assertEquals(expectedHead, snake.get(0));

            // check if the result is as expected (no collision)
            assertEquals(null, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}