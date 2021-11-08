import java.awt.event.ActionEvent;

/**
 * A controller for rolling the dice command.
 * @author Daniah Mohammed #101145902
 * @version 1.0
 */


public class RollController extends MonopolyController{
    /**
     * A constructor that creates a RollController object.
     * @param model Monopoly, the model to control.
     */
    public RollController(Monopoly model) {
        super(model);
    }

    /**
     * Roll the Dice and return the value of it.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.roll();
    }
}
