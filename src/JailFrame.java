import javax.swing.*;

public class JailFrame extends JFrame implements MonopolyView {
    private Monopoly monopoly;
    private Jail jail;

    public JailFrame(Monopoly monopoly, Jail jail) {
        super("Jail");
        this.monopoly = monopoly;
        this.jail = jail;
        setSize(200, 200);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    private void construct(Player activePlayer) {
        JOptionPane.showMessageDialog(this, "You are in Jail!");

        if (jail.waitEscape(activePlayer)) {
            JOptionPane.showMessageDialog(this, "You waited 3 days and were let out thanks to your warden's kindness.");
        }
        else {

            JOptionPane.showMessageDialog(this, "Please select an option to get out of Jail!", "To Get Out of Jail", JOptionPane.WARNING_MESSAGE);
            int result1 = JOptionPane.showConfirmDialog(null, "Do you want to try rolling a double?");
            switch (result1) {
                case JOptionPane.YES_OPTION -> {
                    System.out.println("Yes");
                    if (jail.rollEscape(activePlayer)) {
                        JOptionPane.showMessageDialog(this, "You rolled doubles and got out of Jail!");
                    } else {
                        JOptionPane.showMessageDialog(this, "You failed to roll doubles and are still in Jail!");
                    }
                }
                case JOptionPane.NO_OPTION -> System.out.println("No");
                case JOptionPane.CANCEL_OPTION -> System.out.println("Cancel");
                case JOptionPane.CLOSED_OPTION -> System.out.println("Closed");
            }
            if (result1 != JOptionPane.YES_OPTION) {
                int result2 = JOptionPane.showConfirmDialog(null, String.format("Do you want to pay fine (" + Monopoly.currencySign + "%d)", Jail.FINE));
                switch (result2) {
                    case JOptionPane.YES_OPTION -> {
                        System.out.println("Yes");
                        if (jail.fineEscape(activePlayer)) {
                            JOptionPane.showMessageDialog(this, String.format("You paid " + Monopoly.currencySign + "%d and got out of Jail!", Jail.FINE));
                        } else {
                            JOptionPane.showMessageDialog(this, "You failed to pay the fine and are still in Jail!");
                        }
                    }
                    case JOptionPane.NO_OPTION -> System.out.println("No");
                    case JOptionPane.CANCEL_OPTION -> System.out.println("Cancel");
                    case JOptionPane.CLOSED_OPTION -> System.out.println("Closed");
                }
            }
        }
    }

    @Override
    public void handleMonopolyUpdate(MonopolyEvent e) {
        if (e.getActivePlayer().getJailId() == jail.getID()
                && !((Monopoly) e.getSource()).isMoved()
                && e.getActivePlayer().getType() != Player.Type.BOT
        ) { //PlayerMoveNeeded() not perfect here...
            construct(e.getActivePlayer());
        }
    }
}
