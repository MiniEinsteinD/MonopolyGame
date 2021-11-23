import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Test class for Property.
 * @author Ethan Leir 101146422 & Daniah Mohammed 101145902
 * @version 2.0
 */
public class PropertyTest {

    Property property;
    Player player;

    /**
     * Set up test attributes.
     */
    @Before
    public void init() {
        property = new Property("A", 50, "b");
        player = new Player("1", "red", Color.RED, new Monopoly());
    }

    /**
     * Test isOwned method for when the property isn't owned and when it is.
     */
    @Test
    public void isOwned() {
        assertFalse(property.isOwned());
        player.buyProperty(new StringBuilder(), property);
        assertTrue(property.isOwned());
    }

    /**
     * Test toString method for an equal string and a different string.
     */
    @Test
    public void testToString() {
        Property temp1 = new Property("A", 50, "b");
        Property temp2 = new Property("B", 20, "z");
        assertEquals(temp1.toString(), property.toString());
        assertNotEquals(temp2.toString(), property.toString());
    }

    /**
     * Test getFine method
     */

    @Test
    public void getFine() {
        assertEquals(5,property.getFine());
    }

}