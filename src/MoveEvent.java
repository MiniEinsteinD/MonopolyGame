import java.util.EventObject;

public class MoveEvent extends EventObject {
    private int steps;
    public MoveEvent(Monopoly source, int steps) {
        super(source);
        this.steps=steps;
    }

    public int getSteps() {
        return steps;
    }
}
