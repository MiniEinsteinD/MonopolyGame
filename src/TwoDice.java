/**
 * This class represents the two dice rolled by the player.
 * @author Umniyah Mohammed (101158792)
 * @version 1.0
 */
public class TwoDice {
    private int firstRoll = 0;
    private int secondRoll = 0;

    /**
     * This constructor creates a new TwoDice.
     */
    public TwoDice(){}

    /**
     * This method checks whether the player rolls doubles (same number on each die) or not.
     * @return true if the player rolls doubles, otherwise false.
     */
    public boolean isDouble () {
        if (firstRoll != secondRoll) {return false;}
        else if (firstRoll == 0) {return false;}
        else {return true;}
    }

    /**
     * This method adds up the total on the top face of each die.
     * @return sum an integer representing the addition of the two dice.
     */
    public int dieSum() {
        int sum = firstRoll + secondRoll;
        return sum;
    }

    /**
     * This method rolls the two dice for the player.
     */
    public void roll () {
        firstRoll = 1 + (int) (Math.random() * 6);
        secondRoll = 1 + (int) (Math.random() * 6);
    }

}