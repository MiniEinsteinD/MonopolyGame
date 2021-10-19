public class Property extends Tile{

    private Player owner;
    private final String GROUP;
    private final int PRICE;
    private boolean owned;


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

}
