import java.awt.event.ActionEvent;

public class SetBuildableTilesController extends MonopolyController {

    Buildable buildable;

    /**
     * A constructor that creates a BuildController object.
     * @param model Monopoly, the model to control.
     */
    public SetBuildableTilesController(Monopoly model, Buildable buildable) {
        super(model);
        this.buildable = buildable;
    }

    /**
     * Display buildable properties.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.build(buildable);
    }
}
