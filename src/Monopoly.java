import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class to set up and play a game of Monopoly.
 * @author Ethan Leir
 * @version 0.2
 */
public class Monopoly {
    private final ArrayList<Tile> TILES;
    private Player activePlayer;
    private ArrayList<Player> players;
    private TwoDice dice;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 8;
    private static final ArrayList<String> COLORS = new ArrayList<>(Arrays.asList("Red", "Green", "Blue", "Yellow",
            "Purple", "Orange", "White", "Black"));

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
    }

    /**
     * Starts the game of Monopoly.
     */
    public void start(){
        Scanner sc = new Scanner(System.in);
        int num_players = 0;
        boolean valid = false;

        System.out.println("Welcome to Digital Monopoly!");
        System.out.println("===================================================================================");

        System.out.println(String.format("How many players would like to participate? Enter an integer between" +
                " {} and {}:", MIN_PLAYERS, MAX_PLAYERS));
        while(!valid) {
            try {
                num_players = sc.nextInt();
                if (MIN_PLAYERS <= num_players && num_players <= MAX_PLAYERS) {
                    valid = true;
                }
            } catch (InputMismatchException ime) {
                System.out.println(String.format("You did not input an integer. Enter an integer between {} and {}:",
                        MIN_PLAYERS, MAX_PLAYERS));
            }
        }

        for (int i = 0; i < num_players; i++){
            players.add(new Player(String.valueOf(i), COLORS.get(i)));
        }

        System.out.println("Have fun :)");
    }

    public static void main(String[] args) {
        Monopoly m = new Monopoly();
        m.start();
    }
}
