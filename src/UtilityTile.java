public class UtilityTile extends Tile{

    private Player owner;
    private final String GROUP;
    private int PRICE;
    private boolean owned;


    public UtilityTile(String name, int startingPrice){
        super(name);
        this.PRICE = startingPrice;
        this.GROUP = "Utility";
        this.owned = false;
    }

    //WIP: IF ONE UTILITY IS OWNED: 4 * DICEROLL
    //IF BOTH UTILITIES OWNED: 10 * DICEROLL
    public int getPrice(){

    }

    public void setOwner(Player p) {
        this.owner = p;
        this.owned = true;
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean isOwned() {
        return owned;
    }


    public String getGroup() {
        return GROUP;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================================");
        sb.append("\nThe property name is: " + getName() + "\nThe price of the property is: "+
                getPrice() + "\nIt is a part of: " + getGroup() +" group");
        if (this.isOwned()){
            sb.append("\nOwned by: " + this.owner.getCOLOR() + "\n");
            sb.append("================================\n");
            return sb.toString();
        }
        sb.append("\nThis property is not owned yet!\n");
        sb.append("================================\n");
        return sb.toString();
    }

    @Override
    public void landHandler(StringBuilder sb, Player player) {
        sb.append(
                "You are now at tile:\n" + this + "\n"
        );

        if (owned && !owner.equals(player)) {
            player.payFine(sb, this);
        }
    }

    @Override
    public void passHandler(StringBuilder sb, Player player){

    }


}
