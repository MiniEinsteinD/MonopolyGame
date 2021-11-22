import javax.swing.*;

/**
 * This class is the Jail tile on the Monopoly board
 * @author Umniyah Mohammed (101158792)
 * @version 1.0
 */
public class Jail extends Tile {

    private final String GROUP;
    private TwoDice twoDice;

    /**
     * This is a constructor for class Jail
     */
    public Jail(String name, TwoDice twoDice){
        super(name);
        this.GROUP = "Jail";
        this.twoDice = twoDice;
    }

    public String getGroup() {
        return GROUP;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================================");
        sb.append("\nThe tile name is: " + getName());
        sb.append("\n================================\n");
        return sb.toString();
    }

    @Override
    public void landHandler(StringBuilder sb, Player player) {
        if (player.getPosition() == 10 && player.getLandedOnGoToJail()) {
            sb.append("You are now at tile:\n" + this + "\n");
            JFrame frame = new JFrame("Jail");
            frame.setSize(200, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            JOptionPane.showMessageDialog(frame, "You are in Jail!");
            JOptionPane.showMessageDialog(frame, "Please select an option to get out of Jail!", "To Get Out of Jail", JOptionPane.WARNING_MESSAGE);
            int result1 = JOptionPane.showConfirmDialog(null, "Do you want to try rolling a double?");
            switch (result1) {
                case JOptionPane.YES_OPTION -> {
                    System.out.println("Yes");
                    twoDice.roll();
                    if (twoDice.isDouble()) {
                        player.setPosition(twoDice.dieSum());
                    } else {
                        player.setPosition(10);
                    }
                }
                case JOptionPane.NO_OPTION -> System.out.println("No");
                case JOptionPane.CANCEL_OPTION -> System.out.println("Cancel");
                case JOptionPane.CLOSED_OPTION -> System.out.println("Closed");
            }
            JOptionPane.showMessageDialog(frame, "Please select an option to get out of Jail!", "To Get Out of Jail", JOptionPane.WARNING_MESSAGE);
            int result2 = JOptionPane.showConfirmDialog(null, "Do you want to pay fine ($50)");
            switch (result2) {
                case JOptionPane.YES_OPTION -> {
                    System.out.println("Yes");
                    player.setWallet(player.getWallet() - 50);
                    player.setPosition(twoDice.dieSum());
                }
                case JOptionPane.NO_OPTION -> System.out.println("No");
                case JOptionPane.CANCEL_OPTION -> System.out.println("Cancel");
                case JOptionPane.CLOSED_OPTION -> System.out.println("Closed");
            }
        }
    }
}
