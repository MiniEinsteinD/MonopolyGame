import javax.swing.*;
import java.awt.*;

/**
 * @author Ethan Houlahan 101145675
 */
public class MonopolyFrame extends JFrame implements MonopolyView{


    private JFrame numPlayerMenu;

    private JFrame propertyViewer;

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

        //Create Select Player Number Menu
        numPlayerMenu = new JFrame("Select Number of Players");
        numPlayerMenu.setSize(new Dimension(300,600));
        numPlayerMenu.setLayout(new GridLayout());

        for (int i = Monopoly.MIN_PLAYERS; Monopoly.MAX_PLAYERS >= i ;i++){
            JButton numButton = new JButton();
            numButton.setText(Integer.toString(i));
            numButton.setSize(new Dimension(20,20));
            int startVal = i;
            numButton.addActionListener(e-> model.start(startVal));
            numPlayerMenu.add(numButton);
        }


        MonopolyController rollCont = new RollController(model);
        MonopolyController buyCont = new BuyController(model);
        MonopolyController passCont = new PassController(model);
        MonopolyController stateCont = new PlayerStateController(model);

        //Button Initialization
        rollButton.setText("Roll Dice");
        rollButton.addActionListener(rollCont);
        buyButton.setText("Buy Current Property");
        buyButton.addActionListener(buyCont);
        passButton.setText("End Turn");
        passButton.addActionListener(passCont);
        playerOverviewButton.setText("View Player Portfolio");
        playerOverviewButton.addActionListener(stateCont);

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
        if (e.getActivePlayer().getWallet() <= 0){
            walletStateText.setText("$" + e.getActivePlayer().getWallet());
            walletStateText.updateUI();
            JOptionPane.showMessageDialog(this,"You Are Bankrupt!\nThanks for playing!","Bankruptcy!",4);
        }else{
            walletStateText.setText("$" + e.getActivePlayer().getWallet());
            walletStateText.updateUI();
        }
        activePlayerText.setText("Current Player: " + e.getActivePlayer().getCOLOR());
        activePlayerText.updateUI();
    }

    public static void main(String[] args) {

        new MonopolyFrame();
    }

}
