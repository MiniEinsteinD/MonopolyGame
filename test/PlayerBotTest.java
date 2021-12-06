import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for PlayerBot.
 * @author Ethan Leir 101146422
 * @version 1.0
 */
public class PlayerBotTest {
    private final int NORMAL_STARTING_WALLET = 5000;
    private final int FAIL_STARTING_WALLET = 820;

    /**
     * Tests selectActions for successfully moving and buying a Buyable. Assumes that Player's start on the GoTile.
     */
    @Test
    public void selectActionsBuySuccess() {
        Monopoly monopoly = new Monopoly();
        //Only bots would create an infinite loop so that isn't allowed in the game. Here, this means that we need to
        //do some finagling to get at the bot player.
        monopoly.start(2,1);
        //Find the bot player
        int bot_index = 0;
        while (bot_index < monopoly.getPlayers().size() && monopoly.getPlayers().get(bot_index).getType() != Player.Type.HUMAN) {
            bot_index++;
        }
        //Move as many times as needed to confirm that the BOT lands on a Buyable Tile.
        int last_index = monopoly.getPlayers().get(bot_index).getPosition();
        while (!(monopoly.getTiles().get(monopoly.getPlayers().get(bot_index).getPosition()) instanceof Buyable)){
            while (monopoly.playerMoveNeeded()) {
                monopoly.roll();
            }
            monopoly.passTurn();

            //Make sure that the bot is moving on their turn.
            assertNotEquals(last_index, monopoly.getPlayers().get(bot_index).getPosition());
            last_index = monopoly.getPlayers().get(bot_index).getPosition();
        }

        assertNotEquals(NORMAL_STARTING_WALLET, monopoly.getPlayers().get(bot_index).getWallet());
        assertEquals(monopoly.getPlayers().get(bot_index).getBuyables().size(), 1);
    }

    /**
     * Tests selectActions for successfully moving and buying a Buyable. Assumes that Player's start on the GoTile.
     * Assumes that no Properties cost less than or equal to FAIL_STARTING_WALLET - PlayerBot.MIN_WALLET.
     */
    @Test
    public void selectActionsBuyFailure() {
        Monopoly monopoly = new Monopoly();
        //Only bots would create an infinite loop so that isn't allowed in the game. Here, this means that we need to
        //do some finagling to get at the bot player.
        monopoly.start(2,1);
        //Find the bot player
        int bot_index = 0;
        while (bot_index < monopoly.getPlayers().size() && monopoly.getPlayers().get(bot_index).getType() != Player.Type.HUMAN) {
            bot_index++;
        }
        //Set the bot player's wallet to FAIL_STARTING_WALLET
        monopoly.getPlayers().get(bot_index).setWallet(FAIL_STARTING_WALLET);
        //Move as many times as needed to confirm that the BOT lands on a Buyable Tile.
        int last_index = monopoly.getPlayers().get(bot_index).getPosition();
        do {
            while (monopoly.playerMoveNeeded()) {
                monopoly.roll();
            }
            monopoly.passTurn();

            //Make sure that the bot is moving on their turn.
            assertNotEquals(last_index, monopoly.getPlayers().get(bot_index).getPosition());
            last_index = monopoly.getPlayers().get(bot_index).getPosition();
        } while (!(monopoly.getTiles().get(monopoly.getPlayers().get(bot_index).getPosition()) instanceof Buyable));

        assertEquals(FAIL_STARTING_WALLET, monopoly.getPlayers().get(bot_index).getWallet());
    }
}