/*Ethan Leir 101146422*/
import javax.swing.text.View;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The parent class of all Monopoly controllers.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public abstract class MonopolyController implements ActionListener {
    Monopoly model;
    ArrayList<View> views;

    /**
     * Creates a MonopolyController object.
     * @param model Monopoly, the model to control.
     * @param views ArrayList<View>, the views contained in the model.
     */
    public MonopolyController(Monopoly model, ArrayList<View> views) {
        this.model = model;
        this.views = views;
    }
}
