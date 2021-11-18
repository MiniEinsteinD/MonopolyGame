import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A class to control a player with AI.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public class PlayerBot {
    // Player won't buy if it would put its wallet below 800.
    private static final int MIN_WALLET = 800;
    private static final int DELAY_TIME = 2;

    /**
     * Control all the actions of the player for their turn. Pauses for some time between each command.
     * @param sae SelectActionsEvent, an EventObject storing all needed information.
     */
    public static void selectActions(SelectActionsEvent sae) {
        Monopoly monopoly = (Monopoly) sae.getSource();
        ArrayList<Tile> tiles = monopoly.getTILES();

        while (monopoly.playerMoveNeeded()) {
            sleep(DELAY_TIME);
            monopoly.roll();

            Tile tileAtPosition = tiles.get(sae.getPlayer().getPosition());
            if (
                    tileAtPosition instanceof Property
                            && !((Property) tileAtPosition).isOwned()
                            && sae.getPlayer().getWallet() - ((Property) tileAtPosition).getPrice() >= MIN_WALLET
            ) {
                sleep(DELAY_TIME);
                monopoly.buy();
            }
        }

        sleep(DELAY_TIME);
        monopoly.passTurn();
    }

    /**
     * Pause for a number of seconds. If it fails because of an interrupt occurring, notifies the user in the terminal.
     * @param seconds int, the number of seconds to sleep for.
     */
    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            System.out.println("Interrupted while sleeping.");
        }
    }
}
