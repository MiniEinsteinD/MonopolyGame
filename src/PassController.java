import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * A controller for passing the turn to the next player command.
 * @author Daniah Mohammed #101145902
 * @version 1.0
 */

public class PassController extends  MonopolyController{
    /**
     * A constructor that creates a PassController object.
     * @param model Monopoly, the model to control.
     */
    public PassController(Monopoly model) {
        super(model);
    }

    /**
     * Pass the turn to the next player.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.passTurn();
    }
}
