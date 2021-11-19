public interface Buyable {

    int getFine();

    Player getOwner();

    void setOwner(Player player);

    boolean isOwned();

    String getGroup();



    int getPrice();



    void remOwner();
}
