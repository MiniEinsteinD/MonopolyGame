import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A class to control selecting a Buildable Tile to build on.
 * @author Daniah Mohammed 101145902, Ethan Leir 101146422
 * @version 2.0
 */
public class SetBuildableTilesController extends MonopolyController {

    private Buildable buildable;
    private JFrame frame;

    /**
     * A constructor that creates a BuildController object.
     * @param model Monopoly, the model to control.
     * @param frame, JFrame, the frame containing the button which uses this ActionListener.
     * @param buildable, Buildable, the Buildable Tile to build on.
     */
    public SetBuildableTilesController(Monopoly model, JFrame frame, Buildable buildable) {
        super(model);
        this.buildable = buildable;
        this.frame = frame;
    }

    /**
     * Display buildable properties.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        model.removeView((MonopolyView) frame);
        model.build(buildable);
    }
}
