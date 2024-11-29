
import com.badlogic.gdx.physics.box2d.World;
import io.github.AngryBirdsCDAbhi.Levels.Level2f;
import io.github.AngryBirdsCDAbhi.Levels.Level3f;
import io.github.AngryBirdsCDAbhi.Piggies.Piggies;
import io.github.AngryBirdsCDAbhi.DynamicBodies;

import io.github.AngryBirdsCDAbhi.Levels.Level1f;
import io.github.AngryBirdsCDAbhi.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AngryBirdTest {

    private Main mainMock;
    private Level1f level1f;
    private Level2f level2f;
    private Level3f level3f;

    private World worldMock;

    @BeforeEach
    public void setUp() {
        mainMock = mock(Main.class);
        worldMock = mock(World.class);
        level1f = new Level1f(mainMock);
        level2f = new Level2f(mainMock);
        level3f = new Level3f(mainMock);

    }

    @Test
    public void testInitialization() {
        assertNotNull(level1f, "Level1f should not be null after initialization");
        assertNotNull(level2f, "Level2f should not be null after initialization");
        assertNotNull(level3f, "Level3f should not be null after initialization");
    }


    @Test
    public void testPigCreation() {
        assertNotNull(level1f.getPiggiesofLevel(), "Piggies list should not be null");
        assertTrue(level1f.getPiggiesofLevel().size() > 0, "Piggies should be created and added to the level");
    }

    @Test
    public void testBlockInitialization() {
        assertNotNull(level1f.getBlocksofLevel(), "Blocks list should not be null");
        assertTrue(level1f.getBlocksofLevel().size() > 0, "Blocks should be created and added to the level");
    }

    @Test
    public void testLevelScoreIncreaseOnPigDeath() {

        Piggies king = mock(Piggies.class);
        when(king.getHealth()).thenReturn(0);
        level1f.getPiggiesofLevel().add(king);
        level1f.kingDie();
        level1f.updatePigs();
        assertEquals(100, Level1f.score, "Score should increase when the king pig dies");
    }

    @Test
    public void testBlockFunctionality() {
        DynamicBodies block = mock(DynamicBodies.class);
        level1f.getBlocksofLevel().add(block);
        assertTrue(level1f.getBlocksofLevel().size() > 0, "There should be blocks in the level");
    }

}
