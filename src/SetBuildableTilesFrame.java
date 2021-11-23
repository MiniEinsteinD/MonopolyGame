import javax.swing.*;
import java.awt.*;

/**
 * A frame to select a Buildable Tile to build on.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public class SetBuildableTilesFrame extends JFrame implements MonopolyView {
    private Monopoly model;

    /**
     * Constructs a SetBuildableTilesFrame object.
     * @param model Monopoly, the Monopoly object which is running the game.
     */
    public SetBuildableTilesFrame(Monopoly model) {
        super("Tiles to build");
        this.model = model;
        JPanel panel = new JPanel();
        setLayout(new GridLayout());
        setSize(new Dimension(300, 300));


        JLabel subLabel = new JLabel("Select Tiles You Wish To Build");
        panel.add(subLabel);

        for(Buildable b: model.getActivePlayer().listOfValidBuildables()){
            JButton button = new JButton(b.toString());
            panel.add(button);
            button.addActionListener(new SetBuildableTilesController(model, this, b));
        }

        JScrollPane scroller = new JScrollPane();
        panel.add(scroller);
        add(panel);
        setVisible(true);
    }

    /**
     * Does nothing since this class is temporarily created, then destructed before a change can occur to the model.
     * @param e MonopolyEvent, stores information used for the view to respond to the event.
     */
    @Override
    public void handleMonopolyUpdate(MonopolyEvent e) {

    }
}
