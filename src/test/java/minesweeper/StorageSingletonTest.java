package minesweeper;

import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class StorageSingletonTest
{
    private StorageSingleton singleton = StorageSingleton.getInstance();

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void singletonIsInstantiated() {
        assertTrue(singleton != null);
    }


    @Test
    public void singletonValueCanBeRead() {
        assertTrue(singleton.animationSpeed == 1050.0);
    }
}
