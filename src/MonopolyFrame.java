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

    private JButton rollButton;
    private JButton buyButton;
    private JButton passButton;
    private JButton playerOverviewButton;
    private JButton helpButton;


    private JLabel walletStateText;
    private JLabel activePlayerText;
    private JLabel boardMap;

    private JPanel boardPane;
    private JPanel infoPane;
    private ImageIcon boardImage;


    public MonopolyFrame() {
        super("Monopoly6 [TEMP NAME]");
        this.setLayout(new BorderLayout());
        Monopoly model = new Monopoly();
        model.addView(this);
        this.setSize(new Dimension(1200, 800));

        //Create Standard font
        Font stdFont = new Font("Comic Sans MS",Font.BOLD,30);
        //Create Select Player Number Menu

        numPlayerMenu = new JFrame("Select Number of Players");
        numPlayerMenu.setSize(new Dimension(300, 600));
        numPlayerMenu.setLayout(new FlowLayout());

        numPanelText = new JLabel("How many people are playing?", SwingConstants.CENTER);
        numPanelText.setFont(stdFont);
        numPlayerMenu.add(numPanelText);

        for (int i = Monopoly.MIN_PLAYERS; Monopoly.MAX_PLAYERS >= i; i++) {
            JButton numButton = new JButton();
            numButton.setText(Integer.toString(i));
            numButton.setSize(new Dimension(50, 50));
            int startVal = i;
            JFrame mainFrameTempVar = this;
            numButton.addActionListener((new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    model.start(startVal);
                    numPlayerMenu.setVisible(false);
                    mainFrameTempVar.setVisible(true);
                }
            }));
            numPlayerMenu.add(numButton);
        }


        MonopolyController rollCont = new RollController(model);
        MonopolyController helpCont = new HelpController(model);
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
        helpButton = new JButton("Help");
        helpButton.addActionListener(helpCont);


        //Panel Initialization
        boardPane = new JPanel();
        infoPane = new JPanel();
        infoPane.setPreferredSize(new Dimension(500, 800));
        boardPane.setLayout(new BorderLayout());
        infoPane.setLayout(new GridLayout(0, 1));
        boardPane.setBackground(Color.BLACK);
        infoPane.setBackground(Color.YELLOW);

        //Label initialization

        activePlayerText = new JLabel("", SwingConstants.CENTER);
        activePlayerText.setFont(stdFont);
        walletStateText = new JLabel("", SwingConstants.CENTER);
        walletStateText.setFont(stdFont);


        //Adding Board Image
        boardImage = new ImageIcon("src/TempboardAwsome.png");
        boardMap = new JLabel(boardImage);


        //Component Addition
        infoPane.add(activePlayerText);
        infoPane.add(walletStateText);
        infoPane.add(rollButton);
        infoPane.add(buyButton);
        infoPane.add(playerOverviewButton);
        infoPane.add(passButton);
        infoPane.add(helpButton);

        boardPane.add(boardMap, BorderLayout.CENTER);


        this.add(boardPane, BorderLayout.CENTER);
        this.add(infoPane, BorderLayout.EAST);

        numPlayerMenu.setDefaultCloseOperation(EXIT_ON_CLOSE);
        numPlayerMenu.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

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

        JOptionPane.showMessageDialog(this,model.getEventString(),"Action Log",1);
    }

    public static void main(String[] args) {

        new MonopolyFrame();
    }

}
