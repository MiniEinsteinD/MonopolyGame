import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test class for Player.
 * @author Ethan Leir 101146422 - (Daniah Mohammed - 101145902 - M3)
 * @version 2.0
 */
public class PlayerTest {

    private Player p1;
    private Player p2;
    private Player p3;
    private Property b1;
    private Property b2;
    private ArrayList<Tile> tiles;
    private StringBuilder sb;

    @Before
    public void init(){
        tiles = new ArrayList<>();
        p1 = new Player("1", "red", new Monopoly());
        p2 = new Player("2", "green", new Monopoly());
        p3 = new Player("1", "red", new Monopoly());
        b1 = new Property("Hi", 20, "Greetings");
        b2 = new Property("Hi", 1000000, "Greetings");
        tiles.add(new Property("A", 20, "Brown"));
        tiles.add(new Property("A.2", 20, "Brown"));
        tiles.add(new Property("B", 30, "Yes"));
        tiles.add(new Property("C", 40, "Maybe"));
        sb = new StringBuilder();
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
        assertTrue(p1.buyProperty(sb, b1));
        assertFalse(p1.buyProperty(sb, b2));
        assertFalse(p2.buyProperty(sb, b1));
    }

    /**
     * Tests the payFine method for if either player's wallet changed, and if a value was subtracted from one and added
     * to another.
     */
    @Test
    public void testPayFine() {
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
        p1.movePlayer(sb, 2, tiles);
        assertEquals(2, p1.getPosition());
        p1.movePlayer(sb, 3, tiles);
        assertEquals(1, p1.getPosition());
        assertEquals(0, p2.getPosition());
    }

    /**
     * Test getGroupsCanBeBuilt method
     */

    @Test
    public void testGetGroupsCanBeBuilt() {
        ArrayList<Buildable> test= new ArrayList<>();

        test.add(new Property("A", 20, "Brown"));
        test.add(new Property("A.2", 20, "Brown"));

        p1.buyProperty(sb, (Buyable) test.get(0));
        p1.buyProperty(sb, (Buyable) test.get(1));

        assertEquals(test,p1.listOfValidBuildables());
    }

    @Test
    public void testListOfValidBuildables() {
        ArrayList<Buildable> test= new ArrayList<>();
        test.add((Buildable) tiles.get(0));
        test.add((Buildable) tiles.get(1));
        p1.buyProperty(sb, (Buyable) tiles.get(0));
        p1.buyProperty(sb, (Buyable) tiles.get(1));
        assertEquals(test, p1.listOfValidBuildables());
    }



    @Test
    public void testReturnPropertiesOnBankrupt() {
        ArrayList<Buildable> test= new ArrayList<>();
        p1.buyProperty(sb, (Buyable) tiles.get(0));
        p1.buyProperty(sb, (Buyable) tiles.get(1));
        p1.returnPropertiesOnBankrupt();
        assertEquals(test, p1.getBuyables());
    }

    @Test
    public void testIsBankrupt() {
        p1.setWallet(-1);
        assertEquals(true, p1.isBankrupt());
        p1.setWallet(0);
        assertEquals(false, p1.isBankrupt());
    }

    @Test
    public void testToString1() {
        assertEquals("Your player ID is: 1\n" +
                "Your balance is: 5000\n" +
                "You are at position: 0\n" +
                "Your color is: red\n" +
                "You do not own any properties yet", p1.toString());
    }

    @Test
    public void buyProperty() {
        ArrayList<Buyable> b = new ArrayList<>();
        b.add((Buyable) tiles.get(0));
        p1.buyProperty(sb, (Buyable) tiles.get(0));
        assertEquals(b, p1.getBuildables());
    }

    @Test
    public void payFine() {
        p1.buyProperty(sb, (Buyable) tiles.get(1));
        assertEquals(true, p2.payFine(sb,  (Buyable) tiles.get(1)) );
        assertEquals(false, p2.payFine(sb,  (Buyable) tiles.get(0)) );
    }


    @Test
    public void checkPropertyInv() {
        p1.buyProperty(sb, (Buyable) tiles.get(0));
        assertEquals(1, p1.checkPropertyInv((Buyable) tiles.get(0)));
    }
    @Test
    public void testEqual() {
        Player test = new Player("1", "red", new Monopoly());
        assertEquals(true, p1.equals(test));
    }
}