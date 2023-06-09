import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * The class player is responsible for the operations that the player
 * will perform throughout the game. e.g. buyProperty, payFine
 *
 * @author Daniah Mohammed
 * St# 101145902
 * (M2 and M3 changes) Ethan Leir
 * St# 101146422
 * @version 6.0
 */
public class Player implements Serializable {
    private final String ID;
    private int wallet = 5000;
    private List<Buyable> buyables;
    private int position;
    private final String COLOR;
    private final Color COLORCODE;
    private Monopoly monopoly;
    private ArrayList<Buildable> buildables;
    protected enum Type {HUMAN, BOT}
    private Type type;
    private int jailId;
    private int daysJailed;


    /**
     * Constructor for class player.
     * @param id, a constant string that represents the player id.
     * @param color, a constant string that represents the player color.
     * @param monopoly, the game of monopoly the player is in.
     */
    public Player(String id, String color, Color colorCode, Monopoly monopoly) {
        this.ID = id;
        this.COLOR = color;
        this.COLORCODE = colorCode;
        this.monopoly = monopoly;
        this.buyables = new ArrayList<Buyable>();
        this.type = Type.HUMAN;
        this.buildables = new ArrayList<>();
        this.jailId = 0;
        this.daysJailed = 0;
    }

    /**
     * Constructor for class player.
     * @param id, a constant string that represents the player id.
     * @param color, a constant string that represents the player color.
     * @param monopoly, the game of monopoly the player is in.
     * @param type, the type of player (human or bot).
     */
    public Player(String id, String color, Color colorCode, Monopoly monopoly, Type type) {
        this.ID = id;
        this.COLOR = color;
        this.COLORCODE = colorCode;
        this.monopoly = monopoly;
        this.type = type;
        this.buyables = new ArrayList<>();
        this.buildables = new ArrayList<>();
        this.jailId = 0;
        this.daysJailed = 0;
    }

    /**
     * A getter for the Color class representation of the player's color.
     * @return the player's color.
     */
    public Color getCOLORCODE() {
        return COLORCODE;
    }

    /**
     * A getter for the player type.
     * @return the type of player (human or bot).
     */
    public Type getType() {
        return type;
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

    public ArrayList<Buildable> getBuildables() {
        return buildables;
    }

    public List<Buyable> getBuyables() {
        return buyables;
    }

    /**
     * A setter of the player's balance
     * @param wallet int value that represents the player balance
     */

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    /**
     * Gets the id of the jail that the player is in.
     * @return int, the id of the jail that the player is in. 0 if the player is not in jail.
     */
    public int getJailId() {
        return this.jailId;
    }

    /**
     * This is a setter for the method jailId.
     * @param jailId, boolean value set to the jailId attribute.
     */
    public void setJailId(int jailId){
        this.jailId = jailId;
    }

    /**
     * Gets the number of days the player has been in jail.
     * @return int, the number of days the player has been in jail.
     */
    public int getDaysJailed() {
        return daysJailed;
    }

    /**
     * Increments the number of days the player has been in jail.
     */
    public void incrementDaysJailed() {
        daysJailed++;
    }

    /**
     * Makes the player a free man.
     */
    public void escapedJail() {
        this.jailId = 0;
        this.daysJailed = 0;
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
        if(buyables.isEmpty()) {
            propertyDescription.append("\nYou do not own any properties yet") ;
        } else {
            propertyDescription.append("\nYou own the following properties: ") ;
            for(Buyable p : buyables) {
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
    public boolean buyProperty(StringBuilder sb, Buyable property) {
        if(!property.isOwned() && this.wallet >= property.getPrice()) {
            if (property instanceof Buildable){
                buildables.add( (Buildable) property);
            }
            this.buyables.add(property);
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

    public boolean payFine(StringBuilder sb, Buyable property) {
        Player owner = property.getOwner();
        int fine = property.getFine();
        if(property.isOwned()){
            if (wallet >= fine ) {
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
        return false;

    }

    /**
     * Pay the fine required to be released from jail.
     * @return boolean, true if the player has enough money and successfully paid the fine,
     *                  false if the player does not have enough money and cannot pay the fine.
     */
    public boolean payJailFine() {
        if (wallet >= Jail.FINE) {
            wallet = wallet - Jail.FINE;
            return true;
        }
        else {
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
        int distance = (position + steps) % tiles.size();
        for (int i = 1; i <= steps; i++) {
            tiles.get((i + position) % tiles.size()).passHandler(sb,this);
        }
        this.setPosition(distance);
        tiles.get(distance).landHandler(sb, this);
    }

    /**
     * This method checks if the player owns all the properties with the same colors
     * in order to build a building.
     * @return ArrayList of the tile colors that the player entirely owns
     * (Daniah Mohammed, #101145902)
     */

    public ArrayList<String> getGroupsCanBeBuilt(){
        ArrayList<String> colorsPlayerCanBuild = new ArrayList<>();
        HashMap<String, Integer> numOfSameColorOwned = new HashMap<>();

        //store the colors of the properties' player owns and their count (How many property of the same color the player owns)
        for (Buildable p: buildables) {
            numOfSameColorOwned.put(p.getGroup(), numOfSameColorOwned.getOrDefault(p.getGroup(), 0) + 1);
        }

        for(String color: numOfSameColorOwned.keySet()){
            if (numOfSameColorOwned.get(color) == monopoly.getNumBuildablesInGroup(color)) {
                colorsPlayerCanBuild.add(color);
            }
        }
        return colorsPlayerCanBuild;
    }

    /**
     * A method that returns a list of buildable properties that the player owns
     * @return ArrayList<Buildable> buildable properties that the player owns
     * Daniah Mohammed (101145902)
     */

    public ArrayList<Buildable> listOfValidBuildables(){
        ArrayList<Buildable> list = new ArrayList<>();
        ArrayList<String> validColors = this.getGroupsCanBeBuilt();
        for(Buildable b: buildables){
            if(validColors.contains(b.getGroup())){
                list.add(b);
            }
        }
        return list;
    }

    /**
     * Remove ownership of tiles when bankrupt
     */
    public void returnPropertiesOnBankrupt(){
        for (Buyable b: buyables){
            b.remOwner();
        }
        buildables.clear();
        buyables.clear();
    }

    /**
     * Get the number of properties with the same group as the passed Buyable tile
     * @param buyable attribute
     * @return
     */
    public int checkPropertyInv(Buyable buyable) {
        int counter = 0;
        for (Buyable p : buyables) {
            if (p.getGroup().equals(buyable.getGroup())){
                counter++;
            }
        }
        return counter;
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
        return wallet == player.wallet && position == player.position && Objects.equals(ID, player.ID) && Objects.equals(buyables, player.buyables) && Objects.equals(COLOR, player.COLOR);
    }

    /**
     * A method that generates a hash code for each player instance.
     * @return an int hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID, wallet, buyables, position, COLOR);
    }


}