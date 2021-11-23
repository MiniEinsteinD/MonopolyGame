/**
 * Interface that refers to the property that could be built by the players
 *
 * @version 1.0
 * @author Daniah Mohammed - 101145902
 */
public interface Buildable {
    void buildHandler(StringBuilder sb);
    String  getGroup();
    int getDevLevel();
}
