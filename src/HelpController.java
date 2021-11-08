/*Ethan Leir 101146422*/
import java.awt.event.ActionEvent;

/**
 * A controller for the help command.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public class HelpController extends MonopolyController {
    /**
     * Creates a HelpController object.
     * @param model Monopoly, the model to control.
     */
    public HelpController(Monopoly model) {
        super(model);
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
