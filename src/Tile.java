
/**
 * The tile class represents the tiles that the players land on in the game.
 *
 * @author Ethan Houlahan 101145675
 * @version 2.0
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

}
