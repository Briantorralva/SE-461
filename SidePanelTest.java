package org.psnbtech;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

public class SidePanelTest {

    private JFrame frame;
    private JPanel panel;
    private SnakeGame game;
    private SidePanel sidePanel;
    private Robot robot;

    @Before
    public void setUp() {
        frame = new JFrame("Snake Game Test");
        game = new SnakeGame();
        sidePanel = new SidePanel(game);
        frame.add(sidePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        panel = new SidePanel(game);
        panel.setPreferredSize(panel.getPreferredSize());
    }

    @Test
    public void testPaintComponent() {
        BufferedImage image = new BufferedImage(sidePanel.getWidth(), sidePanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        sidePanel.paintComponent(g);

        assertEquals("Snake Game", "Snake Game");
        assertEquals(Color.BLACK, sidePanel.getBackground());
    }

    @Test
    public void testStatistics() {
        assertEquals(0, game.getScore());
        assertEquals(0, game.getFruitsEaten());
        assertEquals(0, game.getNextFruitScore());
    }

    @Test
    public void testControls() {
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);
    }
}

