import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test class for Player.
 * @author Ethan Leir 101146422
 * @version 1.1
 */
public class PlayerTest {

    private Player p1;
    private Player p2;
    private Player p3;
    private Property b1;
    private Property b2;
    private ArrayList<Tile> tiles;

    @Before
    public void init(){
        p1 = new Player("1", "red", Color.RED, new Monopoly());
        p2 = new Player("2", "green", Color.GREEN, new Monopoly());
        p3 = new Player("1", "red", Color.RED, new Monopoly());
        b1 = new Property("Hi", 20, "Greetings");
        b2 = new Property("Hi", 1000000, "Greetings");
        tiles.add(new Property("A", 20, "No"));
        tiles.add(new Property("B", 30, "Yes"));
        tiles.add(new Property("C", 40, "Maybe"));
    }

    /**
     * Tests the ToString method for one equal String, one empty String, and one non-empty non-equal String.
     */
    @Test
    public void testToString() {
        assertEquals(p1.toString(), p3.toString());
        assertNotEquals("", p1.toString());
        assertNotEquals(p1.toString(), p2.toString());
    }

    /**
     * Tests the buyProperty method for one purchasable Property, one too expensive Property, and one already owned
     * Property.
     */
    @Test
    public void testBuyProperty() {
        StringBuilder sb = new StringBuilder();
        int walletBefore = p1.getWallet();
        assertTrue(p1.buyProperty(sb, b1));
        assertEquals(walletBefore - 5, p1.getWallet());
        assertFalse(p1.buyProperty(sb, b2));
        assertFalse(p2.buyProperty(sb, b1));
    }

    /**
     * Tests the payFine method for if either player's wallet changed, and if a value was subtracted from one and added
     * to another.
     */
    @Test
    public void testPayFine() {
        StringBuilder sb = new StringBuilder();
        p1.buyProperty(sb, b1);
        int p1WalletBefore = p1.getWallet();
        int p2WalletBefore = p2.getWallet();
        p2.payFine(sb, b1);
        assertNotEquals(p1WalletBefore, p1.getWallet());
        assertNotEquals(p2WalletBefore, p2.getWallet());
        assertEquals(p1.getWallet() - p1WalletBefore, p2WalletBefore - p2.getWallet());
    }

    /**
     * Tests the movePlayer method for moving a normal amount of steps, and overflow.
     */
    @Test
    public void testMovePlayer() {
        StringBuilder sb = new StringBuilder();
        p1.movePlayer(sb, 2, tiles);
        assertEquals(2, p1.getPosition());
        p1.movePlayer(sb, 3, tiles);
        assertEquals(2, p1.getPosition());
    }

}