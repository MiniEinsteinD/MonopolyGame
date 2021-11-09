import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Property.
 * @author Ethan Leir 101146422
 * @version 1.0
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
        player = new Player("1", "red", new Monopoly());
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

}