import java.util.ArrayList;

/**
 * @author Daniah Mohammed
 * St# 101145902
 */
public class Player{
    private final String id;
    private int wallet;
    private ArrayList<Property> properties = new ArrayList<>();
    private int position;
    private final String color;

    public Player(String id, String color){
        this.id = id;
        this.color=color;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void movePlayer(int steps){
        this.position += steps;
    }
}
