/*Ethan Leir 101146422*/
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * A controller for choosing the number of players.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public class NumOfPlayersController extends MonopolyController {
    /**
     * Creates a NumOfPlayersController object.
     * @param model Monopoly, the model to control.
     */
    public NumOfPlayersController(Monopoly model) {
        super(model);
    }

    /**
     * Construct a game of Monopoly of the selected size.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String num = e.getActionCommand();

        model.start(Integer.parseInt(num));
    }
}
