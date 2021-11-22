
/**
 * The GO tile is the starting tile for all the players
 * @author Ethan Houlahan
 * St# 101145675
 * @version 1.0
 */
public class GoTile extends Tile{
    private static final  String goName = "GO";

    public GoTile(){
        super(goName);
    }

    /**
     * dictates the action taken when a player lands on this tile
     * @param sb StringBuilder, stores the string to be displayed to the user.
     * @param player Player, the player who landed on the tile.
     */
    @Override
    public void landHandler(StringBuilder sb, Player player) {
        sb.append(
                "You are now at tile:\n" + this + "\n"
        );
    }

    /**
     * dictates what happens when a player passes this tile
     * @param sb StringBuilder, stores the string to be displayed to the user.
     * @param player Player, the player who passed the tile.
     */
    @Override
    public void passHandler(StringBuilder sb, Player player){
        player.setWallet(player.getWallet() + 200);

        sb.append("You Passed Go and collected $200!\n");
    }
}
