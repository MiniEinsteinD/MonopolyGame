/*Ethan Leir 101146422*/
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * A controller for the buy command.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public class BuyController extends MonopolyController {
    /**
     * Creates a BuyController object.
     * @param model Monopoly, the model to control.
     */
    public BuyController(Monopoly model) {
        super(model);
    }

    /**
     * The active player purchases a property.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.buy();
    }
}
