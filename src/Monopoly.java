/* Monopoly class */
/* Ethan Leir 101146422 */
import java.util.*;
import java.awt.Color;

/**
 * A class to set up and play a game of Monopoly.
 * @author Ethan Leir 101146422
 * @version 5.0
 */
public class Monopoly {
    private final ArrayList<Tile> TILES;
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
    protected static int lastRoll;

    /**
     * Constructs a Monopoly object.
     */
    public Monopoly(){
        TILES = new ArrayList<>(Arrays.asList(
                new Property("Dunton Tower",60,"Brown"),
                new Property("Jack's Truss",60,"Brown"),
                new Property("Tokyo-1",100,"Light Blue"),
                new Property("Tokyo-2",100,"Light Blue"),
                new Property("Tokyo-3",120,"Light Blue"),
                new Property("Lavina Crescent",140,"Pink"),
                new Property("Banner Road",140,"Pink"),
                new Property("Bronson Avenue",160,"Pink"),
                new Property("Mondstadt",180,"Orange"),
                new Property("Liyue",180,"Orange"),
                new Property("Inazuma",200,"Orange"),
                new Property("East Blue",220,"Red"),
                new Property("West Blue",220,"Red"),
                new Property("Grand Line",240,"Red"),
                new Property("North Blue",260,"Yellow"),
                new Property("South Blue",260,"Yellow"),
                new Property("Red Line",280,"Yellow"),
                new Property("Umniyah Crescent",300,"Green"),
                new Property("Daniah Road",300,"Green"),
                new Property("Ethan Avenue",320,"Green"),
                new Property("Howling Abyss",350,"Purple"),
                new Property("Summoner's Rift",400,"Purple")
        ));
        activePlayer = null;
        players = new ArrayList<>();
        dice = new TwoDice();
        numSolventPlayers = 0;
        activePlayerIndex = 0;
        running = false;
        eventString = "";
        views = new ArrayList<MonopolyView>();
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
        for (Tile tile : TILES) {
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
        return !moved || dice.isDouble();
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
    public ArrayList<Tile> getTILES() {
        return TILES;
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
            Tile t = TILES.get(activePlayer.getPosition());
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
        StringBuilder sb= new StringBuilder();
        if (playerMoveNeeded()) {
            generateRoll(sb);
            move(sb);
            moved = true;
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
        activePlayer.movePlayer(sb, dice.dieSum(), TILES);
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

            sb.append(activePlayer.getCOLOR() + " player's turn!\n" );

            eventString = sb.toString();
            notifyViews();

            handleBotActions();
        }
    }

    /**
     * Update the information in all of the Monopoly's views.
     */
    private void notifyViews(){
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
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        activePlayerIndex = rand.nextInt(numPlayers);
        numSolventPlayers = numPlayers;
        sb.append("Player colors to choose from are ");
        for (int i = 0; i < numPlayers; i++){
            if (i < numPlayers - numBots){
                sb.append(COLORS.get(i) + " ");
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


    public void build(Buildable buildable) {
        StringBuilder sb = new StringBuilder();
        buildable.buildHandler(sb);
        eventString = sb.toString();
        notifyViews();
    }
}
