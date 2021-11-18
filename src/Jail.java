/**
 * This class is the Jail tile on the Monopoly board
 * @author Umniyah Mohammed (101158792)
 * @version 1.0
 */
public class Jail extends Tile{

    private int goToJail;
    private final String GROUP;

    /**
     * This is a constructor for class Jail
     * @param destination, int that represents the destination the player will move to
     */
    public Jail(String name, int destination){
        super(name);
        this.goToJail = destination;
        this.GROUP = "Jail";
    }

    public String getGroup() {
        return GROUP;
    }

    /**
     * This is a getter for destination.
     * @return destination, int
     */
    public int getDestination(){
        return goToJail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================================");
        sb.append("\nThe tile name is: " + getName());
        sb.append("\n================================\n");
        return sb.toString();
    }

    @Override
    public void landHandler(StringBuilder sb, Player player) {
        super.landHandler(sb, player);
    }

    @Override
    public void passHandler(StringBuilder sb, Player player) {
    }
}
