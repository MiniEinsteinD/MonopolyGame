public class RailroadTile extends Tile implements Buyable{

    private Player owner;
    private final String GROUP;
    private final int PRICE;
    private final int baseRent;
    private boolean owned;

    public RailroadTile(String name, int startingPrice) {
        super(name);
        this.PRICE = startingPrice;
        this.GROUP = "Railroad";
        this.owned = false;
        this.baseRent = 250;
    }

    public int getPrice(){
        return PRICE;
    }


    public void setOwner(Player p) {
        this.owner = p;
        this.owned = true;
    }


    public void remOwner(){
        this.owned = false;
        this.owner = null;
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean isOwned() {
        return owned;
    }



    public String getGroup(){
        return GROUP;
    }

    //WIP: EVERY RAILROAD OWNED ON THE BOARD DOUBLES RENT FOR EVERY RR OWNED

    public int getFine(){

        int numRROwned = owner.checkPropertyInv(this);
        int rent = baseRent;

        if (numRROwned >= 2){
            rent = PRICE * 2;
        }
        if (numRROwned >= 3){
            rent = PRICE * 2;
        }
        if (numRROwned == 4){
            rent = PRICE * 2;
        }
        return rent;
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
}
