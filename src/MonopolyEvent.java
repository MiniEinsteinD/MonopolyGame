import java.util.EventObject;

public class MonopolyEvent extends EventObject {

    private Player activePlayer;

    public MonopolyEvent(Monopoly monopoly){
        super(monopoly);
        activePlayer = monopoly.getActivePlayer();
    }

    public Player getActivePlayer(){
        return this.activePlayer;
    }
}
