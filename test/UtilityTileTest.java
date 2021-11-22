import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Testing UtilityTile class
 * @version 1.0
 * (Daniah Mohammed - 101145902 - M3)
 */
public class UtilityTileTest{
    private UtilityTile utilityTile;
    private Player p1;
    private Player p2;
    private Player p3;
    private Property b1;
    private Property b2;
    private ArrayList<Tile> tiles;
    private StringBuilder sb;
    private Monopoly monopoly;

    @Before
    public void init(){
        tiles = new ArrayList<>();
        utilityTile = new UtilityTile("1", 100);
        p1 = new Player("1", "red", Color.RED, new Monopoly());
        tiles.add(utilityTile);
        sb = new StringBuilder();
        monopoly = new Monopoly();
    }


    /**
     * Test for remOwner and isOwned methods
     */
    @Test
    public void testRemOwnerAndIsOwned() {
        utilityTile.setOwner(p1);
        assertEquals(p1, utilityTile.getOwner());

        utilityTile.remOwner();
        assertEquals(null, utilityTile.getOwner());
        assertEquals(false, utilityTile.isOwned());
    }

    @Test
    public void getFine() {
        utilityTile.setOwner(p1);
        assertEquals(0, utilityTile.getFine() );

        monopoly.generateRoll(sb);
        assertEquals(monopoly.getLastRoll()*40, utilityTile.getFine());
    }

    @Test
    public void testToString() {
        assertEquals("\n" +
                "================================\n" +
                "The property name is: 1\n" +
                "The price of the property is: 100\n" +
                "It is a part of: Utility group\n" +
                "This property is not owned yet!\n" +
                "================================\n", utilityTile.toString());
    }

    @Test
    public void landHandler() {

        //TODO
    }

    @Test
    public void testEqual() {
        UtilityTile utilityTile1 = new UtilityTile("1", 100);
        assertEquals(true, utilityTile.equals(utilityTile1));
    }

}