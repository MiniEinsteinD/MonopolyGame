import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * A controller for displaying the active player state command.
 * @author Daniah Mohammed #101145902
 * @version 1.0
 */

public class PlayerStateController extends MonopolyController {
    /**
     * A constructor that creates a PlayerStateController object.
     * @param model Monopoly, the model to control.
     * @param views ArrayList<View>, the views contained in the model.
     */
    public PlayerStateController(Monopoly model, ArrayList<View> views) {
        super(model, views);
    }

    /**
     * Display the active player state.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.state();
    }
}
