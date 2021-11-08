import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * A controller for rolling the dice command.
 * @author Daniah Mohammed #101145902
 * @version 1.0
 */


public class RollController extends MonopolyController{
    /**
     * A constructor that creates a RollController object.
     * @param model Monopoly, the model to control.
     * @param views ArrayList<View>, the views contained in the model.
     */
    public RollController(Monopoly model, ArrayList<View> views) {
        super(model, views);
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
