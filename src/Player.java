import java.util.ArrayList;
import java.util.Objects;

/**
 * The class player is responsible for the operations that the player
 * will perform throughout the game. e.g. buyProperty, payFine
 *
 * @author Daniah Mohammed
 * St# 101145902
 */
public class Player{
    private final String ID;
    private int wallet;
    private ArrayList<Property> properties = new ArrayList<>();
    private int position;
    private final String COLOR;

    /**
     * Constructor for class player.
     * @param id, a constant string that represents the player id.
     * @param color, a constant string that represents the player color.
     */
    public Player(String id, String color){
        this.ID = id;
        this.COLOR =color;
    }


    /**
     * A getter for the player constant color
     * @return a string that contains the color
     */
    public String getCOLOR() {
        return COLOR;
    }

    /**
     * A setter for the player position
     * @param position, int value that represents the current
     *                  position of th player [ASK ABOUT THE NUMBERS OF THE POSITIONS]
     */

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * A getter of the player position
     * @return int value that represents the current position of th player
     */

    public int getPosition() {
        return position;
    }

    /**
     * A getter of the player's balance
     * @return int value that represents the player balance
     */

    public int getWallet() {
        return wallet;
    }

    /**
     * A setter of the player's balance
     * @param wallet int value that represents the player balance
     */

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    /**
     * A method that prints the player's current state
     * @return a string that contains the player's state [properties owned, ID, color, balance, position]
     */
    @Override
    public String toString() {
        String propertyDescription= "";
        for( Property p : properties){
            propertyDescription = p.toString();
        }
        return "Your player ID is: "+ ID + "\nYour balance is: " + wallet + "\nYou are at position: " + position +
                "\nYour color is: " + COLOR + "\nYou own the following properties: " + propertyDescription;
    }

    /**
     * A method that helps the player to purchase a property
     * @param property, the property that is being sold
     * @return true if the player can afford the property price AND if the property is not owned [property sold successfully];
     *          else, the method returns false [issue with buying the property]
     */

    public boolean buyProperty(Property property){
        if(!property.isOwned() && this.wallet >= property.getPrice() ){
            this.properties.add(property);
            this.wallet = wallet - property.getPrice();
            return true;
        }
        return false;
    }

    /**
     * A method where the player pays a fine to the property owner if he is standing on other player's property
     * @param property, the property that the player is positioned on
     * @param owner, the player who owns the property
     * @return boolean, true if the player has enough money to play the fine to the property owner; [paying the fine successfully]
     *          else, return false [player losses the game since he has a negative balance]
     */

    public boolean payFine(Property property, Player owner){
        int fine = (int) (property.getPrice() * Property.FINE_PERCENTAGE);
        if(wallet >= fine ){
            this.wallet = wallet - fine;
            owner.setWallet(owner.getWallet() + fine);
            return true;
        }
        else{
            this.wallet = wallet - fine;
            owner.setWallet(owner.getWallet() + fine);
            return false;
        }
    }

    /**
     * A method that moves the player from his current position
     * @param steps, int value that represents how many steps the player has to move from their current position
     */

    public void movePlayer(int steps){
        this.setPosition(position+steps);
    }

    /**
     * A method that compares two player instances
     * @param o, an object type variable.
     * @return true if both player's entire attributes are equal;
     *         else, it returns false
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return wallet == player.wallet && position == player.position && Objects.equals(ID, player.ID) && Objects.equals(properties, player.properties) && Objects.equals(COLOR, player.COLOR);
    }

    /**
     * A method that generates a hash code for each player instance.
     * @return an int hash code value for the object
     */

    @Override
    public int hashCode() {
        return Objects.hash(ID, wallet, properties, position, COLOR);
    }
}
