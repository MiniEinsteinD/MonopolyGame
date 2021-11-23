import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A controller for displaying the properties that the player can build on.
 * @author Daniah Mohammed #101145902
 * Edited by: Ethan Leir 101146422
 * @version 2.0
 */


public class BuildController extends MonopolyController {

    /**
     * A constructor that creates a BuildController object.
     * @param model Monopoly, the model to control.
     */
    public BuildController(Monopoly model) {
        super(model);
    }

    /**
     * Display buildable properties.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.addView(new SetBuildableTilesFrame(model));
    }
}
