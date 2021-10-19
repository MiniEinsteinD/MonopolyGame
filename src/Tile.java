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
