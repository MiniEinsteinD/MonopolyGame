/**
 * This class tests Monopoly class
 * @author Umniyah Mohammed (101158792)
 * @version 1.0
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class MonopolyTest {

    private Monopoly monopoly;
    private ArrayList<Player> players;

    @Before
    public void init() {
        players = new ArrayList<>();
        players.add(new Player("1", "red", monopoly));
        players.add(new Player("2", "green", monopoly));
        players.add(new Player("3", "blue", monopoly));
        players.add(new Player("4", "yellow", monopoly));
        monopoly = new Monopoly();
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
     * This method tests state
     */
    @Test
    public void testState() {
        monopoly.start(2);
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
     * This method tests buy
     */
    @Test
    public void testBuy() {
        monopoly.start(4);
        monopoly.roll();
        String priorEventString = monopoly.getEventString();
        int wallet = monopoly.getActivePlayer().getWallet();
        monopoly.buy();
        assertNotEquals(wallet, monopoly.getActivePlayer().getWallet());
        assertNotEquals(priorEventString, monopoly.getEventString());
    }

    /**
     * This method tests roll
     * Assumption: there are more than 12 tiles in the game
     */
    @Test
    public void testRoll() {
        monopoly.start(8);
        int priorPosition = monopoly.getActivePlayer().getPosition();
        String priorEventString = monopoly.getEventString();
        monopoly.roll();
        assertNotEquals(priorPosition, monopoly.getActivePlayer().getPosition());
        assertNotEquals(priorEventString, monopoly.getEventString());
    }

    /**
     * This method tests bankrupt
     */
    @Test
    public void testBankrupt() {
        monopoly.start(4);
        int before = monopoly.getNumSolventPlayers();
        StringBuilder sb = new StringBuilder();
        monopoly.bankrupt(sb);
        assertEquals(before - 1, monopoly.getNumSolventPlayers());
    }

}