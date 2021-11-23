/**
 * Interface that refers to the property that could be bought by the players
 *
 * @version 1.0
 * @author Daniah Mohammed - 101145902
 */
public interface Buyable {

    int getFine();
    Player getOwner();
    void setOwner(Player player);
    boolean isOwned();
    String getGroup();
    int getPrice();
    void remOwner();

}
