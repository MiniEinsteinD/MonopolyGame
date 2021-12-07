/* Monopoly class */
/* Ethan Leir 101146422 */
import java.io.*;
import java.util.*;
import java.awt.Color;

/**
 * A class to set up and play a game of Monopoly.
 * @author Ethan Leir 101146422
 * @version 5.0
 */
public class Monopoly implements Serializable {
    private ArrayList<Tile> tiles;
    private Player activePlayer;
    private ArrayList<Player> players;
    private TwoDice dice;
    private boolean running;
    private boolean moved;
    private String eventString;
    private int numSolventPlayers;
    private int activePlayerIndex;
    protected static final int MIN_PLAYERS = 2;
    protected static final int MAX_PLAYERS = 8;
    private static final ArrayList<String> COLORS = new ArrayList<>(Arrays.asList("red", "green", "blue", "yellow",
            "purple", "orange", "white", "black"));
    private static final ArrayList<Color> COLORCODES = new ArrayList<Color>(Arrays.asList(Color.RED, Color.GREEN,
        Color.BLUE, Color.YELLOW, new Color(128, 0, 128), Color.ORANGE, Color.WHITE, Color.BLACK));
    private ArrayList<MonopolyView> views;
    private ArrayList<Jail> jails; // Required to correctly set up the JailViews.
    public VersionFormat versionFormat;
    protected static int lastRoll;
    public static String currencySign;


    /**
     * Constructs a Monopoly object.
     */
    public Monopoly(){
        Jail jail = new Jail("Jail", 1, this);
        /*tiles = new ArrayList<Tile>(Arrays.asList(
                new GoTile(),
                new Property("Dunton Tower",60,"Brown"),
                new Property("Jack's Truss",60,"Brown"),
                new RailroadTile("Train Station", 100),
                new Property("Tokyo-1",100,"Light Blue"),
                new Property("Tokyo-2",100,"Light Blue"),
                new Property("Tokyo-3",120,"Light Blue"),
                jail,
                new Property("Lavina Crescent",140,"Pink"),
                new Property("Banner Road",140,"Pink"),
                new Property("Bronson Avenue",160,"Pink"),
                new RailroadTile("Bus Station", 100),
                new Property("Mondstadt",180,"Orange"),
                new Property("Liyue",180,"Orange"),
                new Property("Inazuma",200,"Orange"),
                new Property("East Blue",220,"Red"),
                new Property("West Blue",220,"Red"),
                new Property("Grand Line",240,"Red"),
                new RailroadTile("Train Station", 100),
                new Property("North Blue",260,"Yellow"),
                new Property("South Blue",260,"Yellow"),
                new Property("Red Line",280,"Yellow"),
                new GoToJail(),
                new Property("Umniyah Crescent",300,"Green"),
                new Property("Daniah Road",300,"Green"),
                new Property("Ethan Avenue",320,"Green"),
                new RailroadTile("Bus Station", 100),
                new Property("Howling Abyss",350,"Purple"),
                new Property("Summoner's Rift",400,"Purple")
        ));
         */
        jails = new ArrayList<>();
        jails.add(jail);
        activePlayer = null;
        players = new ArrayList<>();
        dice = new TwoDice();
        numSolventPlayers = 0;
        activePlayerIndex = 0;
        running = false;
        eventString = "";
        views = new ArrayList<>();
        versionFormat = new VersionFormat();
        currencySign = versionFormat.getCurrencySign();
    }

    /**
     * importing the XMl format from VersionFormat Class
     *
     * @author Daniah Mohammed - 101145902
     * @param fileName, string of the file name that contains the corresponding XML
     */
    public void importFormat(String fileName){
        versionFormat.importFormat(fileName);
        this.tiles = versionFormat.getTiles();
    }

    /**
     * Adds a MonopolyView to the list of views.
     * @param mv MonopolyView, the view to be added.
     */
    public void addView(MonopolyView mv){
        views.add(mv);
    }

    /**
     * Removes a MonopolyView from the list of views.
     * @param mv MonopolyView, the view to be removed.
     */
    public void removeView(MonopolyView mv){
        views.remove(mv);
    }

    /**
     * Creates all JailFrames and adds them to views.
     */
    public void setupJailViews() {
        for (Jail jail : jails) {
            this.addView(new JailFrame(this, jail));
        }
    }

    /**
     * Sets whether the active player has moved or not.
     * @param moved boolean, true if the active player has moved,
     *                       false if the active player has not moved.
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     * Gets whether the active player has moved or not.
     * @return boolean, true if the active player has moved,
     *                  false if the active player has not moved.
     */
    public boolean isMoved() {
        return moved;
    }

    /**
     * Counts the number of Buildable's stored in TILES who are a part of a given group.
     * @param group String, the group to check for.
     * @return int, the number of Buildable's in the group in TILES.
     */
    public int getNumBuildablesInGroup(String group) {
        /*
        Space complexity is beginning to be a concern so instead of making this fast and storing the computed numbers
        in a HashMap for O(1) lookup I decided to calculate the value each time the function is called. It should only
        be used whenever the build command is called, which should be infrequent anyway.
         */
        int count = 0;
        for (Tile tile : tiles) {
            if (tile instanceof Buildable && ((Buildable) tile).getGroup().equals(group)) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * A getter method to get the last roll value
     * @return last roll value rep using int
     */
    public static int getLastRoll() {
        return lastRoll;
    }

    /**
     * Get a String containing any events that took place as a result of the previous action.
     * @return String, any events occurring as a result of the previous action.
     */
    public String getEventString() {
        return eventString;
    }

    /**
     * Get whether the game is still running.
     * @return boolean, true if the game is running,
     *                  false if the game has ended.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Get whether the player must move before they end their turn
     * @return boolean, true if a player action is needed,
     *                  false if the player can end their turn.
     */
    public boolean playerMoveNeeded() {
        return (!moved || dice.isDouble()) && activePlayer.getJailId() == 0;
    }

    /**
     * Get all of the players in the game.
     * @return ArrayList<Player>, a list of all of the players in the game.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Get the number of players who aren't bankrupt.
     * @return int, the number of solvent players.
     */
    public int getNumSolventPlayers() {
        return numSolventPlayers;
    }

    /**
     * Get the active player.
     * @return Player, the active player.
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Get all the tiles on the board, listed in order.
     * @return ArrayList<Tile>, the playing board.
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Prints the state of the active player.
     */
    public void state() {
        StringBuilder sb = new StringBuilder();
        sb.append(activePlayer);
        eventString = sb.toString();
        notifyViews();
    }

    /**
     * Buys a property for the active player.
     */
    public void buy() {
        StringBuilder sb = new StringBuilder();
        if (!moved){
            sb.append("You haven't rolled yet.\n");
        } else {
            Tile t = tiles.get(activePlayer.getPosition());
            if (t instanceof Property) {
                activePlayer.buyProperty(sb, (Property) t);
            } else {
                sb.append("That is not for sale!\n");
            }
        }
        eventString = sb.toString();
        notifyViews();
    }

    /**
     * Rolls two dice then moves the player based on the result.
     */
    public void roll(){
        StringBuilder sb = new StringBuilder();
        if (playerMoveNeeded()) {
            generateRoll(sb);
            moved = true;
            move(sb);
        } else {
            sb.append("You have already rolled.");
        }
        eventString = sb.toString();
        notifyViews();
    }

    /**
     * Rolls two dice and prints the outcome.
     * @param sb StringBuilder, stores the string to be displayed to the user.
     */
    public void generateRoll(StringBuilder sb){
        dice.roll();
        lastRoll = dice.dieSum();
        sb.append(String.format("You rolled %d with %s!\n",
                dice.dieSum(),
                dice.isDouble()? "doubles": "no doubles"
        ));
    }

    /**
     * Prints that the active player is bankrupt and ends the game if there is one solvent player remaining.
     * @param sb StringBuilder, stores the string to be displayed to the user.
     */
    public void bankrupt(StringBuilder sb){
        numSolventPlayers--;
        activePlayer.returnPropertiesOnBankrupt();

        if (numSolventPlayers == 1){
            running = false;

            int winnerIndex = 0;
            while (players.get(winnerIndex).isBankrupt() && winnerIndex < players.size() - 1) {
                winnerIndex++;
            }
            sb.append("Game over! The " + players.get(winnerIndex).getCOLOR()+ " player wins!\n");
        }
    }

    /**
     * Moves the player, prints the new location, and pays any rent.
     * @param sb StringBuilder, stores the string to be displayed to the user.
     */
    private void move(StringBuilder sb){
        sb.append("Moving the " + activePlayer.getCOLOR() + " player...\n" );
        activePlayer.movePlayer(sb, dice.dieSum(), tiles);
    }

    /**
     * Moves the player, prints the new location, and pays any rent.
     * @param steps int, the number of steps to move the player.
     */
    public void directMove(int steps) {
        StringBuilder sb = new StringBuilder();
        sb.append("Moving the " + activePlayer.getCOLOR() + " player...\n" );
        activePlayer.movePlayer(sb, steps, tiles);
        moved = true;
        eventString = sb.toString();
    }

    /**
     * Clear the values on the dice.
     */
    private void resetDice() {
        this.dice = new TwoDice();
    }

    /**
     * Passes the active player's turn to the next solvent player.
     */
    public void passTurn() {
        StringBuilder sb = new StringBuilder();
        if (playerMoveNeeded()) {
            sb.append("You haven't rolled yet.");
        } else {
            activePlayerIndex = (activePlayerIndex + 1) % players.size();
            activePlayer = players.get(activePlayerIndex);
            while (activePlayer.isBankrupt()) {
                activePlayerIndex = (activePlayerIndex + 1) % players.size();
                activePlayer = players.get(activePlayerIndex + 1);
            }
            moved = false;
            resetDice();

            sb.append(activePlayer.getCOLOR() + " player's turn!\n" );

            eventString = sb.toString();
            notifyViews();

            handleBotActions();
        }
    }

    /**
     * Update the information in all of the Monopoly's views.
     */
    public void notifyViews(){
        for (MonopolyView mv : views){
            mv.handleMonopolyUpdate(new MonopolyEvent(this));
        }
    }

    /**
     * Display information on all of the different commands available to the player.
     */
    public void help(){
        StringBuilder sb = new StringBuilder("Roll: Rolls the 2 dice to get how many steps the player will move, " +
                "if you get roll doubles you roll again!\n");
        sb.append("View Player Portfolio: Displays the player's name, wallet, position and properties owned by the player\n");
        sb.append("Buy Property: Active player attempts to buy the property they are standing on. Does not work if you don't have " +
                "enough money, or the property is already owned.\n");
        sb.append("View Available Tile To Build: Displays the available tiles the active player can build on. Must own all properties in a group before building.\n");
        sb.append("End turn: Passes the active player's turn to the next solvent player. Must roll first! \n");

        eventString = sb.toString();
        notifyViews();
    }

    /**
     * Initializes required fields and starts a game of Monopoly with the chosen number of players.
     * @param numPlayers int, the selected number of players.
     */
    public void start(int numPlayers, int numBots) {
        assert tiles.size() != 0;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        activePlayerIndex = rand.nextInt(numPlayers);
        numSolventPlayers = numPlayers;
        sb.append("Players:\n");
        for (int i = 0; i < numPlayers; i++){
            if (i < numPlayers - numBots){
                sb.append(COLORS.get(i) + "\n");
                players.add(new Player(String.valueOf(i + 1), COLORS.get(i), COLORCODES.get(i),
                        this, Player.Type.HUMAN));
            }
            else {
                players.add(new Player(String.valueOf(i + 1), COLORS.get(i), COLORCODES.get(i),
                        this, Player.Type.BOT));
            }
        }
        activePlayer = players.get(activePlayerIndex);

        sb.append("\n");
        sb.append("Have fun!\n");

        running = true;
        eventString = sb.toString();
        notifyViews();

        handleBotActions();
    }

    /**
     * If the active player is a bot, control all the actions of the player for their turn.
     */
    private void handleBotActions() {
        if (activePlayer.getType() == Player.Type.BOT) {
            PlayerBot.selectActions(new SelectActionsEvent(this, activePlayer));
        }
    }

    /**
     * A method that builds buildings on the selected and validated tiles
     * @param buildable The buildable tiles that will be built
     */
    public void build(Buildable buildable) {
        StringBuilder sb = new StringBuilder();
        buildable.buildHandler(sb);
        eventString = sb.toString();
        notifyViews();
    }

    /**
     * save a Monopoly game (object) using serialization
     * @param fileName
     * @throws Exception
     */
    public void exportMonopoly(String fileName) throws Exception {
        FileOutputStream fos = null;
        StringBuilder sb = new StringBuilder();
        File f = new File(fileName +".ser");
        if(f.exists() && !f.isDirectory()) {
            sb.append(fileName + " has been overwritten\n");
        }
        fos = new FileOutputStream(fileName + ".ser");
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
        fos.close();
        sb.append(fileName + " has been successfully saved");
        eventString = sb.toString();
    }

    /**
     * load a previously saved monopoly game from files using serialization
     * @param fileName
     * @return the saved Monopoly object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Monopoly importMonopoly(String fileName) throws Exception {
        FileInputStream fis = null;
        File f = new File(fileName + ".ser");
        if(!f.exists() && !f.isDirectory()) {
            throw new Exception("FILE "+ fileName +" DOES NOT EXIST");
        }
        fis = new FileInputStream(fileName + ".ser");
        ObjectInputStream ois = null;
        ois = new ObjectInputStream(fis);
        Monopoly monopoly = null;
        monopoly = (Monopoly) ois.readObject();
        ois.close();
        fis.close();

        return monopoly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monopoly monopoly = (Monopoly) o;
        return running == monopoly.running && moved == monopoly.moved && numSolventPlayers == monopoly.numSolventPlayers && activePlayerIndex == monopoly.activePlayerIndex && Objects.equals(tiles, monopoly.tiles) && Objects.equals(activePlayer, monopoly.activePlayer) && Objects.equals(players, monopoly.players) && Objects.equals(dice, monopoly.dice) && Objects.equals(eventString, monopoly.eventString) && Objects.equals(views, monopoly.views) && Objects.equals(jails, monopoly.jails) && Objects.equals(versionFormat, monopoly.versionFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiles, activePlayer, players, dice, running, moved, eventString, numSolventPlayers, activePlayerIndex, views, jails, versionFormat);
    }
}