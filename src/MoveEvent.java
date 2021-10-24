import java.util.ArrayList;
import java.util.EventObject;

public class MoveEvent extends EventObject {
    private int steps;
    private int playingBoardSize;
    public MoveEvent(Monopoly source, int steps, int playingBoardSize) {
        super(source);
        this.steps = steps;
        this.playingBoardSize = playingBoardSize;
    }

    public int getSteps() {
        return steps;
    }

    public int getPlayingBoardSize() {
        return playingBoardSize;
    }
}
