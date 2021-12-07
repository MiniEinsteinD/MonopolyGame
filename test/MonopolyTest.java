import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * This class tests Monopoly class
 * @author (Umniyah Mohammed (101158792) - M2); (Daniah Mohammed - 101145902 - M3)
 * @version 2.0
 *
 * Note: Some methods were not tested because rolling dice can not be expected,
 * and most of the methods inside the none tested methods were tested previously.
 * (e.g. most of the methods that build method includes were tested earlier in Player and Property Classes.
 */
public class MonopolyTest {

    private Monopoly monopoly;
    private ArrayList<Player> players;
    private Buildable b;

    @Before
    public void init() {

        players = new ArrayList<>();
        players.add(new Player("1", "red", Color.RED, monopoly));
        players.add(new Player("2", "green", Color.GREEN, monopoly));
        players.add(new Player("3", "blue", Color.BLUE, monopoly));
        players.add(new Player("4", "yellow", Color.YELLOW, monopoly));
        monopoly = new Monopoly();
        b = new Property("A", 20, "Brown");
    }

    /**
     * This method tests the constructor.
     */
    @Test
    public void testConstructor() {
        assertEquals(null, monopoly.getActivePlayer());
        assertEquals(new ArrayList<>(), monopoly.getPlayers());
        assertEquals(0, monopoly.getNumSolventPlayers());
        assertFalse(monopoly.isRunning());
        assertEquals("", monopoly.getEventString());
    }

    /**
     * This method tests state method
     */
    @Test
    public void testState() {
        monopoly.start(2, 0);
        //activePlayer is random at the start of the game so finagling is needed.
        if (monopoly.getActivePlayer().equals(players.get(0))){
            monopoly.state();
            assertEquals(players.get(0).toString(), monopoly.getEventString());
        } else {
            monopoly.state();
            assertEquals(players.get(1).toString(), monopoly.getEventString());
        }
    }

    /**
     * This method tests buy method
     */
    @Test
    public void testBuy() {
        monopoly.start(4, 0);
        monopoly.roll();
        String priorEventString = monopoly.getEventString();
        int wallet = monopoly.getActivePlayer().getWallet();
        monopoly.buy();
        assertNotEquals(wallet, monopoly.getActivePlayer().getWallet());
        assertNotEquals(priorEventString, monopoly.getEventString());
    }

    /**
     * This method tests roll method
     * Assumption: there are more than 12 tiles in the game
     */
    @Test
    public void testRoll() {
        monopoly.start(8, 0);
        int priorPosition = monopoly.getActivePlayer().getPosition();
        String priorEventString = monopoly.getEventString();
        monopoly.roll();
        assertNotEquals(priorPosition, monopoly.getActivePlayer().getPosition());
        assertNotEquals(priorEventString, monopoly.getEventString());
    }

    /**
     * This method tests bankrupt method
     */
    @Test
    public void testBankrupt() {
        monopoly.start(4, 0);
        int before = monopoly.getNumSolventPlayers();
        StringBuilder sb = new StringBuilder();
        monopoly.bankrupt(sb);
        assertEquals(before - 1, monopoly.getNumSolventPlayers());
    }
    /**
     * This method tests isRunning method
     */
    @Test
    public void isRunning() {
        assertEquals(false, monopoly.isRunning());
    }

    /**
     * This method tests playerMoveNeeded method
     */
    @Test
    public void testPlayerMoveNeeded() {
        monopoly.start(2, 0);
        assertEquals(true,monopoly.playerMoveNeeded());

        monopoly.passTurn();
        assertEquals(true,monopoly.playerMoveNeeded());
    }

    /**
     * Test the start method in monopoly
     */

    @Test
    public void start() {
        monopoly.start(2, 0);
        assertEquals(2, monopoly.getNumSolventPlayers());
    }

    /**
     * Test if serialization export and import returns an equivalent monopoly object
     * @throws Exception
     */
    @Test
    public void serializationTest() throws Exception {
        String testName = "SerialTestFile";
        monopoly.exportMonopoly(testName);
        Monopoly newMonopoly = Monopoly.importMonopoly(testName);

        assertEquals(monopoly,newMonopoly);

        File f = new File(testName);
        f.delete();
    }

}