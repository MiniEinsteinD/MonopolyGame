/* Monopoly class */
/* Ethan Leir 101146422 */
import java.util.*;

/**
 * A class to set up and play a game of Monopoly.
 * @author Ethan Leir 101146422
 * @version 3.0
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
    private ArrayList<MonopolyView> views;


    /**
     * Constructs a Monopoly object.
     */
    public Monopoly(){
        TILES = new ArrayList<>(Arrays.asList(
                new Property("Mediteranean Avenue",1600,"Residence"),
                new Property("Baltic Avenue",160,"Residence"),
                new Property("Oriental Avenue",1000,"Residence"),
                new Property("Vermont Avenue",1000,"Architecture"),
                new Property("Connecticut Avenue",1200,"Cafeteria"),
                new Property("St. Charles Place",1400,"Cafeteria"),
                new Property("States Avenue",1400,"Engineering"),
                new Property("Virginia Avenue",1400,"Engineering"),
                new Property("St. James Place",1800,"Business"),
                new Property("Tennessee Avenue",1800,"Business"),
                new Property("NY Avenue",2000,"Business"),
                new Property("Kentucky Avenue",2200,"Business"),
                new Property("Indian Avenue",2200,"Business"),
                new Property("Illinois Avenue",2200,"Residence"),
                new Property("Atlantic Avenue",1600,"Architecture"),
                new Property("Ventnor Avenue",1600,"Business"),
                new Property("Marvin Avenue",1800,"Business"),
                new Property("Pacific Avenue",1300,"Residence"),
                new Property("North Carolina Avenue",1600,"Business"),
                new Property("Pennsylvania Avenue",1200,"Engineering"),
                new Property("Park Place",1300,"Engineering"),
                new Property("Boardwalk",1400,"Cafeteria")
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
     * @return
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
        if (!moved || dice.isDouble()) {
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
    private void generateRoll(StringBuilder sb){
        dice.roll();
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
    public void passTurn(){
        StringBuilder sb = new StringBuilder();
        if (!moved || dice.isDouble()) {
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
            sb.append(
                    "==================================================================================="
            );

            eventString = sb.toString();
            notifyViews();
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
        StringBuilder sb = new StringBuilder("state: Prints the state of the active player.\n");
        sb.append("roll: Rolls two dice to determine how many steps to move the active player, " +
                "prints the new location, and pays any rent. If you rolled doubles, roll again.\n");
        sb.append("buy: Buys a property for the active player. Does not work if you don't have " +
                "enough money, or the property is already owned.\n");
        sb.append("pass: Passes the active player's turn to the next solvent player.\n");

        eventString = sb.toString();
        notifyViews();
    }

    /**
     * Initializes required fields and starts a game of Monopoly with the chosen number of players.
     * @param numPlayers int, the selected number of players.
     */
    public void start(int numPlayers) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        activePlayerIndex = rand.nextInt(numPlayers);
        numSolventPlayers = numPlayers;
        sb.append("Player colors to choose from are ");
        for (int i = 0; i < numPlayers; i++){
            sb.append(COLORS.get(i) + " ");
            players.add(new Player(String.valueOf(i + 1), COLORS.get(i), this));
        }
        activePlayer = players.get(activePlayerIndex);

        sb.append("\n");
        sb.append("Have fun!\n");

        running = true;
        eventString = sb.toString();
        notifyViews();
    }
}
