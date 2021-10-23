import java.util.*;

/**
 * A class to set up and play a game of Monopoly.
 * @author Ethan Leir
 * @version 0.5
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
                new Property("Temp1",1,"Train"),
                new Property("Temp2",2,"Train"),
                new Property("Temp3",3,"Train")
        ));
        activePlayer = null;
        players = new ArrayList<>();
        dice = new TwoDice();
        numSolventPlayers = 0;
        activePlayerIndex = 0;
    }

    /**
     * Prints the state of the active player.
     */
    private void state() {
        System.out.println("¯\\_(ツ)_/¯");
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
        System.out.printf("You rolled %d with %s!",
                dice.dieSum(),
                dice.isDouble()? "doubles": "no doubles"
                );
    }

    private void bankrupt(){
        System.out.println("You're bankrupt!");
        System.out.printf("The %s player loses the game.\n", activePlayer.getCOLOR());
        numSolventPlayers--;
    }

    /**
     * Moves the player and prints the new location and pays any re.
     */
    private void move(){
        System.out.printf("Moving the %s player...\n", activePlayer.getCOLOR());
        activePlayer.movePlayer(dice.dieSum());
        System.out.printf(
                "You are now at tile %d - %s.\n",
                activePlayer.getPosition(),
                TILES.get(activePlayer.getPosition()).getName()
        );

        Tile tileAtPosition = TILES.get(activePlayer.getPosition());
        if (tileAtPosition instanceof Property && !((Property) tileAtPosition).getOwner().equals(activePlayer)) {

            boolean response = activePlayer.payFine((Property) tileAtPosition, ((Property) tileAtPosition).getOwner());

            System.out.printf("You paid a fine to %s.\n", ((Property) tileAtPosition).getOwner());

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
            System.out.println("The recognized commands are 'state', 'roll', 'buy', and 'pass'.");

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
                default:
                    System.out.println("Command not recognized, please try again.");

                    if (numSolventPlayers == 1){
                        running = false;
                        System.out.printf("Game over! The %s player wins!\n", activePlayer.getCOLOR());
                    }
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
            System.out.print(COLORS.get(i));
            players.add(new Player(String.valueOf(i + 1), COLORS.get(i)));
        }

        System.out.println(". Have fun!");

        gameLoop();
    }

    public static void main(String[] args) {
        Monopoly m = new Monopoly();
        m.start();
    }
}
