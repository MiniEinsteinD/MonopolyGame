import java.awt.event.ActionEvent;

/**
 * A controller for displaying the active player state command.
 * @author Daniah Mohammed #101145902
 * @version 1.0
 */

public class PlayerStateController extends MonopolyController {
    /**
     * A constructor that creates a PlayerStateController object.
     * @param model Monopoly, the model to control.
     */
    public PlayerStateController(Monopoly model) {
        super(model);
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
