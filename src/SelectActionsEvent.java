import java.util.EventObject;

/**
 * Object storing information for a player to be controlled by the computer.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public class SelectActionsEvent extends EventObject {
    private Player player;

    /**
     * Creates an SelectActionsEvent object.
     * @param monopoly Monopoly, the game of Monopoly.
     * @param player Player, the player to control.
     */
    public SelectActionsEvent(Monopoly monopoly, Player player){
        super(monopoly);
        this.player = player;
    }

    /**
     * Get the player to be controlled by the computer.
     * @return Player, the player to be controlled by the computer.
     */
    public Player getPlayer() {
        return player;
    }
}
