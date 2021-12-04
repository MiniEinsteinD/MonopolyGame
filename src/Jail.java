import javax.swing.*;

/**
 * This class is the Jail tile on the Monopoly board
 * @author Umniyah Mohammed (101158792)
 * @version 1.0
 */
public class Jail extends Tile {

    public static final int FINE = 50;
    public static final int WAIT_RELEASE_NUM_DAYS = 3;
    private final int ID;
    private Monopoly monopoly;
    private TwoDice dice;

    /**
     * This is a constructor for class Jail
     */
    public Jail(String name, int id, Monopoly monopoly){
        super(name);
        assert (id > 0); //Must be a positive integer so that we can identify players who aren't in any jail.
        this.ID = id;
        this.monopoly = monopoly;
        this.dice = new TwoDice();
    }

    public boolean waitEscape(Player activePlayer) {
        if (activePlayer.getDaysJailed() == WAIT_RELEASE_NUM_DAYS) {
            activePlayer.escapedJail();
            return true;
        }
        return false;
    }

    public boolean rollEscape(Player activePlayer) {
        dice.roll();
        if (dice.isDouble()) {
            activePlayer.escapedJail();
            monopoly.setMoved(true);
            monopoly.directMove(dice.dieSum());
            monopoly.notifyViews();
            return true;
        }
        activePlayer.incrementDaysJailed();
        monopoly.setMoved(true);
        return false;
    }

    public boolean fineEscape(Player activePlayer) {
        if (activePlayer.payJailFine()) {
            activePlayer.escapedJail();
            monopoly.setMoved(true);
            return true;
        }
        activePlayer.incrementDaysJailed();
        monopoly.setMoved(true);
        return false;
    }

    /**
     * Get the id of the jail.
     * @return int, the jail id.
     */
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================================");
        sb.append("\nThe tile name is: " + getName());
        sb.append("\n================================\n");
        return sb.toString();
    }
}
