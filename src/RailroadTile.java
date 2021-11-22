import java.util.Objects;

/**
 * The RailroadTile is a special type of purchasable tile within the game
 * @author Ethan Houlahan
 * St# 101145675
 * @version 1.0
 */
public class RailroadTile extends Tile implements Buyable{

    private Player owner;
    private final String GROUP;
    private final int PRICE;
    private final int BASERENT;
    private boolean owned;

    public RailroadTile(String name, int startingPrice) {
        super(name);
        this.PRICE = startingPrice;
        this.GROUP = "Railroad";
        this.owned = false;
        this.BASERENT = 250;
    }

    /**
     * Get price of tile
     * @return int price of tile
     */
    public int getPrice(){
        return PRICE;
    }


    /**
     * Set owner of tile after purchase.
     * @param p the player purchasing the property
     */
    public void setOwner(Player p) {
        this.owner = p;
        this.owned = true;
    }

    /**
     * Remove the owner from the property
     */
    public void remOwner(){
        this.owned = false;
        this.owner = null;
    }

    /**
     * Get the owner of the property
     * @return the Player owner of the property
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * check if property is owned or not
     * @return boolean
     */
    public boolean isOwned() {
        return owned;
    }


    /**
     * get group of tile
     * @return String group
     */
    public String getGroup(){
        return GROUP;
    }


    /**
     * get fine to pay if land on tile
     * @return int fine to pay
     */
    public int getFine(){
        int rent = BASERENT;
        for (int numRROwned = owner.checkPropertyInv(this); numRROwned > 1; numRROwned-- ){
            rent = rent * 2;
        }
        return rent;
    }

    /**
     * Get string version of the RailroadTile object
     * @return String statement about a RailroadTile
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================================");
        sb.append("\nThe property name is: " + getName() + "\nThe price of the property is: "+
                getPrice() + "\nIt is a part of: " + getGroup() +" group");
        if (this.isOwned()){
            sb.append("\nOwned by: " + this.owner.getCOLOR() + "\n");
            sb.append("================================\n");
            return sb.toString();
        }
        sb.append("\nThis property is not owned yet!\n");
        sb.append("================================\n");
        return sb.toString();
    }

    /**
     * Dictates the operation that occurs when a player lands on this tile
     * @param sb StringBuilder, stores the string to be displayed to the user.
     * @param player Player, the player who landed on the tile.
     */
    @Override
    public void landHandler(StringBuilder sb, Player player) {
        sb.append(
                "You are now at tile:\n" + this + "\n"
        );

        if (owned && !owner.equals(player)) {
            player.payFine(sb, this);
        }
    }

    /**
     * check if this object is equal to another object
     * @param o, object to be compared
     * @return boolean equality value
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RailroadTile that = (RailroadTile) o;
        return PRICE == that.PRICE && BASERENT == that.BASERENT && owned == that.owned && Objects.equals(owner, that.owner) && Objects.equals(GROUP, that.GROUP);
    }

    /**
     * get the hashcode of the object
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(owner, GROUP, PRICE, BASERENT, owned);
    }
}
