/**
 * This class is the GoTo Jail tile (square) on the Monopoly board
 * @author Umniyah Mohammed (101158792)
 * @version 1.0
 */
public class GoToJail extends Tile{
    private int jailPosition;

    GoToJail() {
        super("GO TO JAIL");
        this.jailPosition = 10;
    }

    GoToJail(int jailPosition) {
        super("GO TO JAIL");
        this.jailPosition = jailPosition;
    }

    /**
     * This method handles a player landing on the Go to Jail tile and sends them to the Jail tile.
     * Displays the string version of the tile the player landed on to the user.
     * @param sb StringBuilder, stores the string to be displayed to the user.
     * @param player Player, the player who landed on the tile.
     */
    @Override
    public void landHandler(StringBuilder sb, Player player) {
        sb.append(
                "You are now at tile:\n" + this + "\n"
        );


        player.setLandedOnGoToJail(true);
        player.setPosition(jailPosition);

    }
}
