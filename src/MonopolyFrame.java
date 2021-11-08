import javax.swing.*;
import java.awt.*;

/**
 * @author Ethan Houlahan 101145675
 */
public class MonopolyFrame extends JFrame implements MonopolyView{

    private JButton rollButton;
    private JButton buyButton;
    private JButton passButton;
    private JButton playerOverviewButton;

    private JLabel walletStateText;
    private JLabel activePlayerText;

    private JPanel boardPane;
    private JPanel infoPane;
    private ImageIcon boardImage;

    public MonopolyFrame(){
        super("Monopoly6 [TEMP NAME]");

        this.setLayout(new BorderLayout());
        Monopoly model = new Monopoly();
        model.addView(this);

        this.setSize(new Dimension(1200 ,800));

        //Button Initialization
        rollButton.setText("Roll Dice");
        rollButton.addActionListener(e -> model.roll());
        buyButton.setText("Buy Current Property");
        buyButton.addActionListener(e -> model.buy());
        passButton.setText("End Turn");
        passButton.addActionListener(e -> model.passTurn());
        playerOverviewButton.setText("View Player Portfolio");
        playerOverviewButton.addActionListener(e -> model.state());

        //Panel Initialization
        boardPane.setSize(800,800);
        infoPane.setSize(400,800);
        infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.LINE_AXIS));
        infoPane.setBackground(Color.YELLOW);

        //Component Addition
        infoPane.add(activePlayerText);
        infoPane.add(walletStateText);

        infoPane.add(rollButton);
        infoPane.add(buyButton);
        infoPane.add(passButton);
        infoPane.add(playerOverviewButton);

        this.add(boardPane);
        this.add(infoPane);


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }


    @Override
    public void handleMonopolyUpdate(MonopolyEvent e){
        Monopoly model = (Monopoly) e.getSource();
        if (e.activePlayer.getWallet() <= 0){
            displayWallet.setText("$" + e.activePlayer.getWallet());
            displayWallet.updateUI();
            JOptionPane.showMessageDialog(this,"You Are Bankrupt!\nThanks for playing!");
        }else{
            displayWallet.setText("$" + e.activePlayer.getWallet());
            displayWallet.updateUI();
        }
        activePlayer.setText("Current Player: " + e.activePlayer.getCOLOR());
        activePlayer.updateUI();
    }

    public static void main(String[] args) {

        new MonopolyFrame();
    }

}
