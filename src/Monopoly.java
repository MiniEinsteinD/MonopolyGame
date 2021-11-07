/* Monopoly class */
/* Ethan Leir 101146422 */
import java.util.*;

/**
 * A class to set up and play a game of Monopoly.
 * @author Ethan Leir 101146422
 * @version 1.0
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
                new Property("Dundas",100,"Residence"),
                new Property("Glengarry",200,"Residence"),
                new Property("Grenville",300,"Residence"),
                new Property("Architecture Building",400,"Architecture"),
                new Property("University Center",500,"Cafeteria"),
                new Property("Residence Cafe",100,"Cafeteria"),
                new Property("Minto",200,"Engineering"),
                new Property("Mackenzie",300,"Engineering"),
                new Property("Dunton Tower",400,"Business"),
                new Property("Nicol Building",500,"Business")
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
     * Get all of the tiles on the board, listed in order.
     * @return
     */
    public ArrayList<Tile> getTILES() {
        return TILES;
    }

    /**
     * Prints the state of the active player.
     */
    public void state() {
        System.out.println(activePlayer);
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
                boolean response = activePlayer.buyProperty((Property) t);
                if (!response) {
                    sb.append("Purchase failed. Are you sure you can afford it and no one owns it already?\n");
                } else {
                    sb.append("You successfully bought the property!\n");
                    sb.append(String.format("New balance: %d\n", activePlayer.getWallet()));
                }
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
        if (!moved || dice.isDouble()) {
            generateRoll();
            move();
            moved = true;
        } else {
            System.out.println("You have already rolled.");
        }
        notifyViews();
    }

    /**
     * Rolls two dice and prints the outcome.
     */
    private void generateRoll(){
        dice.roll();
        System.out.printf("You rolled %d with %s!\n",
                dice.dieSum(),
                dice.isDouble()? "doubles": "no doubles"
        );
    }

    /**
     * Prints that the active player is bankrupt and ends the game if there is one solvent player remaining.
     */
    private void bankrupt(){
        System.out.println("You're bankrupt!");
        System.out.printf("The %s player loses the game.\n", activePlayer.getCOLOR());
        numSolventPlayers--;

        if (numSolventPlayers == 1){
            running = false;

            int winnerIndex = 0;
            while (players.get(winnerIndex).getWallet() < 0 && winnerIndex < players.size() - 1) {
                winnerIndex++;
            }
            System.out.printf("Game over! The %s player wins!\n", players.get(winnerIndex).getCOLOR());
        }
    }

    /**
     * Moves the player, prints the new location, and pays any rent.
     */
    private void move(){
        System.out.printf("Moving the %s player...\n", activePlayer.getCOLOR());
        activePlayer.movePlayer(dice.dieSum(), TILES.size());

        Tile tileAtPosition = TILES.get(activePlayer.getPosition());

        /*This should be updated when we add a Tile that isn't a property*/
        if (tileAtPosition instanceof Property){
            System.out.printf(
                    "You are now at tile %d.\n%s\n",
                    activePlayer.getPosition(),
                    ((Property) tileAtPosition)
            );
        } else {
            System.out.printf(
                    "You are now at tile %d. - %s.\n",
                    activePlayer.getPosition(),
                    TILES.get(activePlayer.getPosition()).getName()
            );
        }

        if (tileAtPosition instanceof Property && ((Property) tileAtPosition).isOwned() &&
                !((Property) tileAtPosition).getOwner().equals(activePlayer)) {

            boolean response = activePlayer.payFine((Property) tileAtPosition, ((Property) tileAtPosition).getOwner());

            System.out.printf("You paid a fine to the %s player.\n", ((Property) tileAtPosition).getOwner().getCOLOR());

            if (response) {
                System.out.printf("New balance: %d\n", activePlayer.getWallet());
            } else {
                bankrupt();
            }
        }
    }

    /**
     * Passes the active player's turn to the next solvent player.
     */
    public void passTurn(){
        if (!moved || dice.isDouble()) {
            System.out.println("You haven't rolled yet.");
        } else {
            activePlayerIndex = (activePlayerIndex + 1) % players.size();
            activePlayer = players.get(activePlayerIndex);
            while (activePlayer.getWallet() < 0) {
                activePlayerIndex = (activePlayerIndex + 1) % players.size();
                activePlayer = players.get(activePlayerIndex + 1);
            }
            moved = false;

            System.out.printf("%s player's turn!\n", activePlayer.getCOLOR());
            System.out.println(
                    "==================================================================================="
            );

            notifyViews();
        }
    }

    /**
     * Update the information in all of the Monopoly's views.
     */
    private void notifyViews(){
        for (MonopolyView mv : views){
            mv.update();
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
     * Plays a game of Monopoly until the victor is decided. DEPRECATED
     */
    private void gameLoop(){
        Scanner sc = new Scanner(System.in);
        moved = false;
        String command;

        System.out.printf("%s player's turn!\n", activePlayer.getCOLOR());
        System.out.println("===================================================================================");
        while (running) {
            System.out.println("Enter a command:");
            System.out.println("The recognized commands are 'state', 'roll', 'buy', 'pass', and 'help'.");

            command = sc.nextLine();
            command = command.toLowerCase().trim();
            switch (command){
                /*Call the method from controller. If the function has any print statements, erase them and append the
                 * string builder instead. Update eventString with the result. Sorry, I feel like it would leave you
                 * with too little to do if I resolved this myself.*/
                case "state":
                    state();
                    break;
                case "roll":
                    roll();
                    break;
                case "buy":
                    buy();
                    break;
                case "pass":
                    passTurn();
                    break;
                case "help":
                    help();
                    break;
                default:
                    /*DEPRECATED*/
                    System.out.println("Command not recognized, please try again.");
            }
        }
    }

    /**
     * Initializes required fields and starts a game of Monopoly with the chosen number of players.
     */
    public void start(int numPlayers){
        StringBuilder sb = new StringBuilder();
        numSolventPlayers = numPlayers;
        sb.append("Player colors to choose from are ");
        for (int i = 0; i < numPlayers; i++){
            System.out.print(COLORS.get(i) + "\t");
            players.add(new Player(String.valueOf(i + 1), COLORS.get(i)));
        }
        activePlayer = players.get(activePlayerIndex);

        sb.append("\n");
        sb.append("Have fun!\n");

        running = true;
        eventString = sb.toString();
        notifyViews();
    }

    /**
     * Creates a Monopoly object and starts a game of Monopoly. DEPRECATED
     * @param args
     */
    public static void main(String[] args) {
        Monopoly m = new Monopoly();
        //m.start();
    }
}
