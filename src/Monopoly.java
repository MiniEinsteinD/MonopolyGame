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
    private int numSolventPlayers;
    private int activePlayerIndex;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 8;
    private static final ArrayList<String> COLORS = new ArrayList<>(Arrays.asList("red", "green", "blue", "yellow",
            "purple", "orange", "white", "black"));

    /**
     * Constructs a Monopoly object.
     */
    public Monopoly(){
        TILES = new ArrayList<>(Arrays.asList(
                new Property("Dundas",100,"Residence"),
                new Property("Glengarry",20,"Residence"),
                new Property("Grenville",30,"Residence"),
                new Property("Architecture Building",40,"Architecture"),
                new Property("University Center",50,"Cafeteria"),
                new Property("Residence Cafe",100,"Cafeteria"),
                new Property("Minto",20,"Engineering"),
                new Property("Mackenzie",30,"Engineering"),
                new Property("Dunton Tower",40,"Business"),
                new Property("Nicol Building",50,"Business")
        ));
        activePlayer = null;
        players = new ArrayList<>();
        dice = new TwoDice();
        numSolventPlayers = 0;
        activePlayerIndex = 0;
        running = false;
    }

    /**
     * Prints the state of the active player.
     */
    private void state() {
        System.out.println(activePlayer);
    }

    /**
     * Buys a property for the active player.
     */
    private void buy() {
        Tile t = TILES.get(activePlayer.getPosition());
        if (t instanceof Property) {
            boolean response = activePlayer.buyProperty((Property) t);
            if (!response) {
                System.out.println("Purchase failed. Are you sure you can afford it and no one owns it already?");
            } else {
                System.out.println("You successfully bought the property!");
                System.out.printf("New balance: %d\n", activePlayer.getWallet());
            }
        } else {
            System.out.println("That is not for sale!");
        }
    }

    /**
     * Rolls two dice and prints the outcome.
     */
    private void roll(){
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
    private void passTurn(){
        activePlayerIndex = (activePlayerIndex + 1) % players.size();
        activePlayer = players.get(activePlayerIndex);
        while (activePlayer.getWallet() < 0) {
            activePlayerIndex = (activePlayerIndex + 1) % players.size();
            activePlayer = players.get(activePlayerIndex + 1);
        }
    }

    /**
     * Plays a game of Monopoly until the victor is decided.
     */
    private void gameLoop(){
        Scanner sc = new Scanner(System.in);
        boolean moved = false;
        String command;

        System.out.printf("%s player's turn!\n", activePlayer.getCOLOR());
        System.out.println("===================================================================================");
        while (running) {
            System.out.println("Enter a command:");
            System.out.println("The recognized commands are 'state', 'roll', 'buy', 'pass', and 'help'.");

            command = sc.nextLine();
            command = command.toLowerCase().trim();
            switch (command){
                case "state":
                    state();
                    break;
                case "roll":
                    if (!moved || dice.isDouble()) {
                        roll();
                        move();
                        moved = true;
                    } else {
                        System.out.println("You have already rolled.");
                    }
                    break;
                case "buy":
                    if (!moved){
                        System.out.println("You haven't rolled yet.");
                    } else {
                        buy();
                    }
                    break;
                case "pass":
                    if (!moved || dice.isDouble()) {
                        System.out.println("You haven't rolled yet.");
                    } else {
                        passTurn();
                        moved = false;

                        System.out.printf("%s player's turn!\n", activePlayer.getCOLOR());
                        System.out.println(
                                "==================================================================================="
                        );
                    }
                    break;
                case "help":
                    System.out.println("state: Prints the state of the active player.");
                    System.out.println("roll: Rolls two dice to determine how many steps to move the active player, " +
                            "prints the new location, and pays any rent. If you rolled doubles, roll again.");
                    System.out.println("buy: Buys a property for the active player. Does not work if you don't have " +
                            "enough money, or the property is already owned.");
                    System.out.println("pass: Passes the active player's turn to the next solvent player.");
                    break;
                default:
                    System.out.println("Command not recognized, please try again.");
            }
        }
    }

    /**
     * Initializes required fields and starts the game of Monopoly.
     */
    public void start(){
        Scanner sc = new Scanner(System.in);
        boolean valid = false;

        System.out.println("Welcome to Digital Monopoly!");
        System.out.println("===================================================================================");

        System.out.printf(
                "How many players would like to participate? Enter an integer between %d and %d:\n",
                MIN_PLAYERS, MAX_PLAYERS
        );
        int numPlayers = 0;
        while(!valid) {
            try {
                numPlayers = sc.nextInt();
                if (MIN_PLAYERS <= numPlayers && numPlayers <= MAX_PLAYERS) {
                    valid = true;
                }
            } catch (InputMismatchException ime) {
                System.out.printf(
                        "You did not input an integer. Enter an integer between %d and %d:\n",
                        MIN_PLAYERS, MAX_PLAYERS
                );
            }
        }
        numSolventPlayers = numPlayers;
        System.out.println("Player colors to choose from are ");
        for (int i = 0; i < numPlayers; i++){
            System.out.print(COLORS.get(i) + "\t");
            players.add(new Player(String.valueOf(i + 1), COLORS.get(i)));
        }
        activePlayer = players.get(activePlayerIndex);

        System.out.print("\n");
        System.out.println("Have fun!");

        running = true;
        gameLoop();
    }

    /**
     * Creates a Monopoly object and starts a game of Monopoly.
     * @param args
     */
    public static void main(String[] args) {
        Monopoly m = new Monopoly();
        m.start();
    }
}
