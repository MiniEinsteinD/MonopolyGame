/**
 * The Building Class represents the buildings that the player can buy after owning the property
 *
 *
 */

public abstract class Building {
    private Property associatedProperty;
    private Player owner;
    public final int PRICE;

    protected static final double FINE_PERCENTAGE = 0.1; // might change


    protected Building(int price) {
        PRICE = price;
    }



    public int getPRICE() {
        return PRICE;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
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
