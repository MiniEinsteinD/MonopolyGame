import java.util.EventObject;

/**
 * MonopolyEvents are used to send the state of the model to the view
 * @author Ethan Houlahan 101145675
 * @version 1.0
 */
public class MonopolyEvent extends EventObject {

    private Player activePlayer;

    public MonopolyEvent(Monopoly monopoly){
        super(monopoly);
        activePlayer = monopoly.getActivePlayer();
    }

    /**
     * get the active player in the model
     * @return the active player
     */
    public Player getActivePlayer(){
        return this.activePlayer;
    }
}
