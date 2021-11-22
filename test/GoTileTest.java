import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
/**
 * Testing GoTile class
 * @version 1.0
 * (Daniah Mohammed - 101145902 - M3)
 */
public class GoTileTest {

    GoTile go;
    Player p;
    StringBuilder sb;
    @Before
    public void init(){
        go = new GoTile();
        p = new Player("1", "red", Color.RED, new Monopoly());
        sb = new StringBuilder();
    }

    @Test
    public void testPassHandler() {
        int walletBefore = p.getWallet();
        go.passHandler(sb, p);
        assertEquals(walletBefore + 200, p.getWallet());
    }
}