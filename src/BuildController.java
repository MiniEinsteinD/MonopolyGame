import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A controller for displaying the properties that the player can build on.
 * @author Daniah Mohammed #101145902
 * @version 1.0
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
        JPanel panel = new JPanel();
        JFrame frame = new JFrame("Tiles to build");
        frame.setLayout(new GridLayout());
        frame.setSize(new Dimension(300, 300));


        JLabel subLabel = new JLabel("Select Tiles You Wish To Build");
        panel.add(subLabel);

        for(Buildable b: model.getActivePlayer().listOfValidBuildables()){
            JButton button = new JButton(b.toString());
            panel.add(button);
            button.addActionListener(new SetBuildableTilesController(model, b));
        }

        JScrollPane scroller = new JScrollPane();
        panel.add(scroller);
        frame.add(panel);
        frame.setVisible(true);
    }
}
