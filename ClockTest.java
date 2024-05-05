package org.psnbtech;

import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.reflect.Field;

public class ClockTest {

    @Test
    public void testReset() {
        Clock clock = new Clock(1.0f); // 1 cycle per second
        clock.update(); // update clock to ensure lastUpdate is set
        clock.reset();
        assertFalse(clock.isPaused());
        assertFalse(clock.peekElapsedCycle()); //no cycles should have elapsed after reset
    }

    @Test
    public void testSetPaused() {
        Clock clock = new Clock(1.0f); // 1 cycle per second
        clock.setPaused(true);
        assertTrue(clock.isPaused());
        clock.setPaused(false);
        assertFalse(clock.isPaused());
    }
    
    
    @Test
    public void testUpdatePaused() throws Exception {
        Clock clock = new Clock(1.0f); // 1 cycle per second
        clock.setPaused(true);
        Field lastUpdateField = Clock.class.getDeclaredField("lastUpdate");
        lastUpdateField.setAccessible(true);
        long lastUpdate = (long) lastUpdateField.get(clock); // get initial last update time
        clock.update(); // update clock while paused
        long newLastUpdate = (long) lastUpdateField.get(clock);
        long tolerance = 10; //  set to milliseconds
        assertTrue(Math.abs(newLastUpdate - lastUpdate) <= tolerance); 
        assertFalse(clock.peekElapsedCycle()); // no cycles should have elapsed when paused
    }
    
    @Test
    public void testHasElapsedCycle() throws Exception {
        Clock clock = new Clock(1.0f); // 1 cycle per second
        Field elapsedCyclesField = Clock.class.getDeclaredField("elapsedCycles");
        elapsedCyclesField.setAccessible(true);
        
        // Test when no cycles have elapsed
        elapsedCyclesField.setInt(clock, 0); // set elapsed cycles to 0
        assertFalse(clock.hasElapsedCycle()); // no cycles have elapsed 
        
        // Test when one cycle has elapsed
        elapsedCyclesField.setInt(clock, 1); // set elapsed cycles to 1
        assertTrue(clock.hasElapsedCycle()); // there should be one elapsed cycle
        assertFalse(clock.hasElapsedCycle()); // no more cycles should have elapsed
    }


}

