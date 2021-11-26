import javax.swing.*;

public class JailFrame extends JFrame implements MonopolyView {
    private Monopoly monopoly;

    public JailFrame(Monopoly monopoly) {
        super("Jail");
        this.monopoly = monopoly;
        setSize(200, 200);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    private void construct() {
        JOptionPane.showMessageDialog(this, "You are in Jail!");
        JOptionPane.showMessageDialog(this, "Please select an option to get out of Jail!", "To Get Out of Jail", JOptionPane.WARNING_MESSAGE);
        int result1 = JOptionPane.showConfirmDialog(null, "Do you want to try rolling a double?");
        switch (result1) {
            case JOptionPane.YES_OPTION -> {
                System.out.println("Yes");
                monopoly.roll();
                if (monopoly.getActivePlayer().getLandedOnGoToJail()) {
                    JOptionPane.showMessageDialog(this, "You rolled doubles and got out of Jail!");
                }
                else {
                    JOptionPane.showMessageDialog(this, "You failed to roll doubles and are still in Jail!");
                }
            }
            case JOptionPane.NO_OPTION -> System.out.println("No");
            case JOptionPane.CANCEL_OPTION -> System.out.println("Cancel");
            case JOptionPane.CLOSED_OPTION -> System.out.println("Closed");
        }
        if (result1 != JOptionPane.YES_OPTION){
            int result2 = JOptionPane.showConfirmDialog(null, "Do you want to pay fine ($50)");
            switch (result2) {
                case JOptionPane.YES_OPTION -> {
                    System.out.println("Yes");
                    monopoly.getActivePlayer().setWallet(monopoly.getActivePlayer().getWallet() - Jail.FINE);
                    monopoly.getActivePlayer().setLandedOnGoToJail(false);
                }
                case JOptionPane.NO_OPTION -> System.out.println("No");
                case JOptionPane.CANCEL_OPTION -> System.out.println("Cancel");
                case JOptionPane.CLOSED_OPTION -> System.out.println("Closed");
            }
        }
    }

    @Override
    public void handleMonopolyUpdate(MonopolyEvent e) {
        if (e.getActivePlayer().getLandedOnGoToJail() && ((Monopoly) e.getSource()).playerMoveNeeded()) {
            construct();
        }
    }
}
