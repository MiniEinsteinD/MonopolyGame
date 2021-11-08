import java.util.ArrayList;
import java.util.Objects;

/**
 * The class player is responsible for the operations that the player
 * will perform throughout the game. e.g. buyProperty, payFine
 *
 * @author Daniah Mohammed
 * St# 101145902
 * (M2 changes) Ethan Leir
 * St# 101146422
 * @version 4.0
 */
public class Player{
    private final String ID;
    private int wallet = 1500;
    private ArrayList<Property> properties = new ArrayList<>();
    private int position;
    private final String COLOR;
    private Monopoly monopoly;


    /**
     * Constructor for class player.
     * @param id, a constant string that represents the player id.
     * @param color, a constant string that represents the player color.
     * @param monopoly, the game of monopoly the player is in.
     */
    public Player(String id, String color, Monopoly monopoly) {
        this.ID = id;
        this.COLOR = color;
        this.monopoly = monopoly;
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
     * Checks if the player is bankrupt and returns true if they are.
     * @return  true if the player is bankrupt,
     *          false if the player isn't bankrupt.
     */
    public boolean isBankrupt() {
        return wallet < 0;
    }

    /**
     * A method that prints the player's current state
     * @return a string that contains the player's state [properties owned, ID, color, balance, position]
     */
    @Override
    public String toString() {
        StringBuilder propertyDescription= new StringBuilder();
        if(properties.isEmpty()) {
            propertyDescription.append("\n    You do not own any properties yet") ;
        } else {
            propertyDescription.append("\nYou own the following properties: ") ;
            for(Property p : properties) {
                propertyDescription.append(p.toString());
            }
        }

        return "Your player ID is: "+ ID + "\nYour balance is: " + wallet + "\nYou are at position: " + position +
                "\nYour color is: " + COLOR + propertyDescription;
    }

    /**
     * A method that helps the player to purchase a property
     * @param sb, stores the string to be displayed to the user.
     * @param property, the property that is being sold
     * @return true if the player can afford the property price AND if the property is not owned [property sold successfully];
     *          else, the method returns false [issue with buying the property]
     */
    public boolean buyProperty(StringBuilder sb, Property property) {
        if(!property.isOwned() && this.wallet >= property.getPrice()) {
            this.properties.add(property);
            this.wallet = wallet - property.getPrice();
            property.setOwner(this);
            sb.append("You successfully bought the property!\n");
            sb.append(String.format("New balance: %d\n", wallet));
            return true;
        }
        sb.append("Purchase failed. Are you sure you can afford it and no one owns it already?\n");
        return false;
    }

    /**
     * A method where the player pays a fine to the property owner if he is standing on other player's property
     * @param sb, stores the string to be displayed to the user.
     * @param property, the property that the player is positioned on
     * @return boolean, true if the player has enough money to play the fine to the property owner; [paying the fine successfully]
     *          else, return false [player losses the game since he has a negative balance]
     */
    public boolean payFine(StringBuilder sb, Property property) {
        Player owner = property.getOwner();
        int fine = property.getFine();
        if (wallet >= fine) {
            this.wallet = wallet - fine;
            owner.setWallet(owner.getWallet() + fine);

            sb.append("You paid a fine to the " + owner.getCOLOR() + " player.\n" );
            sb.append("New balance: " + wallet + "\n" );
            return true;
        }
        else {
            this.wallet = wallet - fine;
            owner.setWallet(owner.getWallet() + fine);

            sb.append("You're bankrupt!\n");
            sb.append("The " + this.getCOLOR() + " player loses the game.\n");
            monopoly.bankrupt(sb);
            return false;
        }
    }

    /**
     * A method that moves the player from his current position to the new position after rolling the die
     * Updated for M2 by Ethan Leir
     * @param sb, stores the string to be displayed to the user.
     * @param steps, how many steps the player has to move from their current position
     * @param tiles, ordered list of tiles on the playing board
     */
    public void movePlayer(StringBuilder sb, int steps, ArrayList<Tile> tiles){
        int distance = (position+steps) % tiles.size();
        for (int i = position + 1; i < steps; i++){
            tiles.get(i).passHandler(sb, this);
        }
        tiles.get(distance).landHandler(sb, this);
        this.setPosition(distance);
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
