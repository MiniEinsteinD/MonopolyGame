import java.util.EventObject;

public class MonopolyEvent extends EventObject {

    Player activePlayer;

    public MonopolyEvent(Monopoly monopoly){
        super(monopoly);
        activePlayer = monopoly.getActivePlayer();
    }
}
