import java.util.Objects;

/**
 * The property object represents the property tiles that the players interact with in the game.
 *
 * @author Ethan Houlahan 101145675, (M2 changes) Ethan Leir 101146422
 * @version 2.0
 */
public class Property extends Tile{

    private Player owner;
    private final String GROUP;
    private final int PRICE;
    private boolean owned;
    protected static final double FINE_PERCENTAGE = 0.1;


    public Property(String name, int price, String group){
        super(name);
        this.PRICE = price;
        this.GROUP = group;
    }

    /**
     * Set owner of tile after purchase.
     * @param p the player purchasing the property
     */
    public void setOwner(Player p){
        this.owner = p;
        this.owned = true;
    }

    /**
     * Get the owner of the property
     * @return the Player owner of the property
     */
    public Player getOwner(){
        return this.owner;
    }

    /**
     * Get true if property has been purchased
     * @return owned boolean value of property
     */
    public boolean isOwned(){
        return owned;
    }

    /**
     * Get the price of the property
     * @return the property's price
     */
    public int getPrice(){
        return PRICE;
    }

    /**
     * Get the property's group
     * @return the grouping that this property belongs to
     */
    public String getGroup(){
        return GROUP;
    }


    /**
     * Get string version of the property object
     * @return String statement about a property tile
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
     * get the fine to be applied to a player
     * @return the int value of the rent
     */
    public int getFine() {
        return (int)(FINE_PERCENTAGE * PRICE);
    }

    /**
     * Check if another object is the same
     * @param p, object to be compared
     * @return true or false depending on the values of the other tile
     */
    @Override
    public boolean equals(Object p) {
        if (p instanceof Property) {
            Property o = (Property) p;
            if (this.PRICE == o.getPrice() && this.GROUP.equals(o.getGroup()) && this.getName().equals(o.getName()) && Objects.equals(this.owner,o.getOwner()) && this.owned == o.isOwned()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Get Property object hashcode
     * @return hashcode of Property tile
     */
    @Override
    public int hashCode() {
        return Objects.hash(owner, GROUP, PRICE, owned);
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
     * Handle a player passing the tile when they move.
     * Does nothing for a tile of type property.
     * @param sb StringBuilder, stores the string to be displayed to the user.
     * @param player Player, the player who landed on the tile.
     */
    @Override
    public void passHandler(StringBuilder sb, Player player){

    }
}
