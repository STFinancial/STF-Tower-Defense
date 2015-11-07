package levelstest;

import static org.junit.Assert.*;

import org.junit.Test;

import levels.Level;

public class LevelTest {

	@Test
	public void testLevel() {
		Level l = new Level();
		assertNotNull(l);
	}

}
