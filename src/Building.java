/**
 * The Building Class represents the buildings that the player can buy after owning the property
 *
 * 
 */

public abstract class Building {
    public final String NAME;
    private Player owner;
    public final int PRICE;
    private boolean owned;
    protected static final double FINE_PERCENTAGE = 0.1; // might change


    protected Building(String name, int price) {
        NAME = name;
        PRICE = price;
    }

    public String getNAME() {
        return NAME;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        this.owned = true;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isOwned(){
        return this.owned;
    }


    //Might Change
    public int getFine() {
        return (int)(FINE_PERCENTAGE * PRICE);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }
}
