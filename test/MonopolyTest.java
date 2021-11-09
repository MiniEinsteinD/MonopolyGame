import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class MonopolyTest {

    private Monopoly monopoly;

    @Before
    public void init() {
        ArrayList<Player> players = new ArrayList<>();
        new Player("player1", "green", monopoly);
        new Player("player2", "red", monopoly);
        new Player("player3", "blue", monopoly);
        new Player("player4", "yellow", monopoly);
        monopoly = new Monopoly();
    }

    /**
     * This method tests getEventString
     */
    @Test
    public void getEventString() {
        assertTrue(monopoly.getEventString() == "");
    }


















}