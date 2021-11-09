import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ethan Houlahan 101145675
 */
public class MonopolyFrame extends JFrame implements MonopolyView{


    private JFrame numPlayerMenu;
    private JLabel numPanelText;

    private JFrame propertyViewer;

    private JButton rollButton;
    private JButton buyButton;
    private JButton passButton;
    private JButton playerOverviewButton;

    private JLabel walletStateText;
    private JLabel activePlayerText;
    private JLabel boardMap;

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
        numPlayerMenu.setLayout(new FlowLayout());

        numPanelText = new JLabel("How many people are playing?",SwingConstants.CENTER);
        numPlayerMenu.add(numPanelText);

        for (int i = Monopoly.MIN_PLAYERS; Monopoly.MAX_PLAYERS >= i ;i++){
            JButton numButton = new JButton();
            numButton.setText(Integer.toString(i));
            numButton.setSize(new Dimension(50,50));
            int startVal = i;
            numButton.addActionListener((new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    model.start(startVal);
                    numPlayerMenu.setVisible(false);
                }
            }));
            numPlayerMenu.add(numButton);
        }


        MonopolyController rollCont = new RollController(model);
        MonopolyController buyCont = new BuyController(model);
        MonopolyController passCont = new PassController(model);
        MonopolyController stateCont = new PlayerStateController(model);

        //Button Initialization
        rollButton = new JButton("Roll Dice");
        rollButton.addActionListener(rollCont);
        buyButton = new JButton("Buy Current Property");
        buyButton.addActionListener(buyCont);
        passButton = new JButton("End Turn");
        passButton.addActionListener(passCont);
        playerOverviewButton = new JButton("View Player Portfolio");
        playerOverviewButton.addActionListener(stateCont);

        //Panel Initialization
        boardPane = new JPanel();
        infoPane = new JPanel();
        boardPane.setPreferredSize(new Dimension(800,800));
        infoPane.setPreferredSize(new Dimension(400,800));
        infoPane.setLayout(new GridLayout(0,1));
        infoPane.setBackground(Color.YELLOW);

        //Label initialization
        activePlayerText = new JLabel();
        walletStateText = new JLabel();

        //Adding Board Image
        boardImage = new ImageIcon("TempboardAwsome.png");
        boardMap = new JLabel(boardImage);

        //Component Addition
        infoPane.add(activePlayerText);
        infoPane.add(walletStateText);
        infoPane.add(rollButton);
        infoPane.add(buyButton);
        infoPane.add(passButton);
        infoPane.add(playerOverviewButton);
        boardPane.add(boardMap, BorderLayout.CENTER);

        this.add(boardPane, BorderLayout.WEST);
        this.add(infoPane, BorderLayout.EAST);

        numPlayerMenu.setDefaultCloseOperation(EXIT_ON_CLOSE);
        numPlayerMenu.pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        numPlayerMenu.setVisible(true);
    }


    @Override
    public void handleMonopolyUpdate(MonopolyEvent e){
        Monopoly model = (Monopoly) e.getSource();
        if (e.getActivePlayer().getWallet() <= 0){
            walletStateText.setText("$" + e.getActivePlayer().getWallet());
            walletStateText.updateUI();
            JOptionPane.showMessageDialog(this,"You Are Bankrupt!\nThanks for playing!","Bankruptcy!",4);
        }else{
            walletStateText.setText("Wallet: $" + e.getActivePlayer().getWallet());
            walletStateText.updateUI();
        }
        activePlayerText.setText("Current Player: " + e.getActivePlayer().getCOLOR());
        activePlayerText.updateUI();
    }

    public static void main(String[] args) {

        new MonopolyFrame();
    }

}
