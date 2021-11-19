
/**
 * The tile class represents the tiles that the players land on in the game.
 *
 * @author Ethan Houlahan 101145675, (M2 changes) Ethan Leir 101146422
 * @version 3.0
 */
public abstract class Tile {

    private final String NAME;

    Tile(String tName){
        this.NAME = tName;
    }

    /**
     * Get the name of the tile object
     * @return the String name of the Tile
     */
    public String getName() {
        return NAME;
    }

    /**
     * Convert the object to a string.
     * @return String, a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================================");
        sb.append("\nThe tile name is: " + getName());
        sb.append("\n================================\n");
        return sb.toString();
    }

    /**
     * Handle a player landing on the tile when they move.
     * Displays the string version of the tile the player landed on to the user.
     * @param sb StringBuilder, stores the string to be displayed to the user.
     * @param player Player, the player who landed on the tile.
     */
    public void landHandler(StringBuilder sb, Player player) {
        sb.append(
                "You are now at tile:\n" + this + "\n"
        );
    }

}
