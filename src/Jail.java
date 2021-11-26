import javax.swing.*;

/**
 * This class is the Jail tile on the Monopoly board
 * @author Umniyah Mohammed (101158792)
 * @version 1.0
 */
public class Jail extends Tile {

    public static final int FINE = 50;

    /**
     * This is a constructor for class Jail
     */
    public Jail(String name){
        super(name);
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
