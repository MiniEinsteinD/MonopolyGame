public class GoTile extends Tile{

    public GoTile(){
        super("GO");
    }

    @Override
    public void landHandler(StringBuilder sb, Player player) {
        sb.append(
                "You are now at tile:\n" + this + "\n"
        );
    }

    @Override
    public void passHandler(StringBuilder sb, Player player){
        player.setWallet(player.getWallet() + 200);
    }
}
