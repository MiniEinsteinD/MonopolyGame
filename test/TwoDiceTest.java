import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * This class tests TwoDice class
 * @author Umniyah Mohammed (101158792)
 * @version 1.0
 */
public class TwoDiceTest {

    private TwoDice dice;
    boolean testDie = true;

    /**
     * This method tests roll
     */
    @Test
    public void testRoll() {
        dice =  new TwoDice();
        for(int i = 0; i < 10000; i++){
            dice.roll();
            int temp = dice.dieSum();
            if (temp < 2 || temp > 12){
                testDie = false;
            }
            assertTrue(testDie);
        }
    }
}