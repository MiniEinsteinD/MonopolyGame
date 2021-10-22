import java.util.Objects;

public class Property extends Tile{

    private Player owner;
    private final String GROUP;
    private final int PRICE;
    private boolean owned;
    //Daniah Added
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


    //Daniah Added this since u were not in the meeting
    @Override
    public String toString() {//Make a print statement
        return "\nYou own the following properties: " +  "\n\n    The property name is: " + getName() + "\n    The price of the property is: "+
                getPrice() + "\n    It is a part from: " + getGroup() +"group";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return PRICE == property.PRICE && owned == property.owned && Objects.equals(owner, property.owner) && Objects.equals(GROUP, property.GROUP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, GROUP, PRICE, owned);
    }
}
