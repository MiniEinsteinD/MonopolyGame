/*Ethan Leir 101146422*/

import java.awt.event.ActionListener;


/**
 * The parent class of all Monopoly controllers.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public abstract class MonopolyController implements ActionListener {
    Monopoly model;

    /**
     * Creates a MonopolyController object.
     * @param model Monopoly, the model to control.
     */
    public MonopolyController(Monopoly model) {
        this.model = model;
    }
}
