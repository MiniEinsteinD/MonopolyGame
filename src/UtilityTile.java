import java.util.Objects;

/**
 * The UtilityTile is a special type of purchasable tile within the game
 * @author Ethan Houlahan
 * St# 101145675
 * @version 1.0
 */
public class UtilityTile extends Tile implements Buyable{

    private Player owner;
    private final String GROUP;
    private int PRICE;
    private boolean owned;


    public UtilityTile(String name, int startingPrice){
        super(name);
        this.PRICE = startingPrice;
        this.GROUP = "Utility";
        this.owned = false;
    }

    /**
     * Get price of tile
     * @return int price of tile
     */
    public int getPrice(){
        return this.PRICE;
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
     * Get price of tile
     * @return String group of tile
     */
    public String getGroup(){
        return GROUP;
    }


    /**
     * get fine to pay if land on tile
     * @return int fine to pay
     */
    public int getFine(){
            if (owner.checkPropertyInv(this) > 1){
                return 100 * Monopoly.lastRoll;
            }
            return 40 * Monopoly.lastRoll;
    }

    /**
     * Get string version of the UtilityTile object
     * @return String statement about a UtilityTile
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
     * Handle a player landing on the property when they move.
     * Displays the tile the player lands on and pays property tax.
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
     * Check if another object is the same
     * @param o, object to be compared
     * @return true or false depending on the values of the other tile
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilityTile that = (UtilityTile) o;
        return PRICE == that.PRICE && owned == that.owned && Objects.equals(owner, that.owner) && Objects.equals(GROUP, that.GROUP);
    }

    /**
     * get hashcode for object
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(owner, GROUP, PRICE, owned);
    }
}
