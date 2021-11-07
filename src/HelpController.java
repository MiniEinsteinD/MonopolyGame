/*Ethan Leir 101146422*/
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * A controller for the help command.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public class HelpController extends MonopolyController {
    /**
     * Creates a HelpController object.
     * @param model Monopoly, the model to control.
     * @param views ArrayList<View>, the views contained in the model.
     */
    public HelpController(Monopoly model, ArrayList<View> views) {
        super(model, views);
    }

    /**
     * Display all commands and how they work.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.help();
    }
}
