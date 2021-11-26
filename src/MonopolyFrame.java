import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * MonopolyFrame is used to generate a graphical interface for user input and display.
 * @author Ethan Houlahan 101145675
 * @version 1.0
 */
public class MonopolyFrame extends JFrame implements MonopolyView {

    private JFrame numPlayerMenu;
    private JLabel numPanelText;
    private JPanel numPanel;

    private JFrame numPlayerBotMenu;
    private JPanel numBotPanel;
    private JLabel numBotPanelText;

    private JButton rollButton;
    private JButton buyButton;
    private JButton passButton;
    private JButton playerOverviewButton;
    private JButton buildButton;
    private JButton helpButton;



    private JLabel walletStateText;
    private JLabel activePlayerText;
    private JLabel boardMap;

    private JPanel boardPane;
    private JPanel infoPane;


    /**
     * Initializes the GUI, Two frames - 1. Number of players, 2. Monopoly game
     */
    public MonopolyFrame() {
        super("Group 6 Monopoly");
        this.setLayout(new BorderLayout());

        Monopoly model = new Monopoly();
        model.addView(this);
        model.addView(new JailFrame(model));
        this.setSize(new Dimension(1200, 800));

        //Create Standard font
        Font stdFont = new Font("Comic Sans MS",Font.BOLD,20);

        //Create Select Player Number Menu
        numPlayerMenu = new JFrame("Select Number of Players");
        numPlayerMenu.setSize(new Dimension(300, 600));
        numPlayerMenu.setLayout(new BorderLayout());
        numPanel = new JPanel();
        numPanel.setBackground(Color.WHITE);
        numPanel.setPreferredSize(new Dimension(300,300));
        numPanel.setLayout(new GridLayout(3,3));
        numPanelText = new JLabel("How many people are playing?", SwingConstants.CENTER);
        numPanelText.setFont(stdFont);
        numPlayerMenu.add(numPanelText,BorderLayout.NORTH);
        numPlayerMenu.add(numPanel,BorderLayout.CENTER);

        numPlayerBotMenu = new JFrame("Select Number of Bots");
        numPlayerBotMenu.setSize(new Dimension(300, 600));
        numPlayerBotMenu.setLayout(new BorderLayout());
        numBotPanel = new JPanel();
        numBotPanel.setBackground(Color.WHITE);
        numBotPanel.setPreferredSize(new Dimension(300,300));
        numBotPanel.setLayout(new GridLayout(3,3));
        numBotPanelText = new JLabel("How many of the players are bots?", SwingConstants.CENTER);
        numBotPanelText.setFont(stdFont);
        numPlayerBotMenu.add(numBotPanelText,BorderLayout.NORTH);
        numPlayerBotMenu.add(numBotPanel,BorderLayout.CENTER);


        for (int i = Monopoly.MIN_PLAYERS; Monopoly.MAX_PLAYERS >= i; i++) {
            JButton numButton = new JButton();
            numButton.setText(Integer.toString(i));
            numButton.setSize(new Dimension(20, 20));
            int numPlayers = i;
            numButton.addActionListener((e -> {
                numPlayerMenu.setVisible(false);

                for (int j = 0; numPlayers > j; j++) {
                    JButton numBotButton = new JButton();
                    numBotButton.setText(Integer.toString(j));
                    numBotButton.setSize(new Dimension(20, 20));
                    int numBots = j;
                    JFrame mainFrameTempVar = this;
                    numBotButton.addActionListener((e1 -> {
                        numPlayerBotMenu.setVisible(false);
                        mainFrameTempVar.setVisible(true);
                        model.start(numPlayers, numBots);
                    }));
                    numBotButton.setBackground(Color.GRAY);
                    numBotPanel.add(numBotButton);
                }
                numPlayerBotMenu.setVisible(true);
            }));
            numButton.setBackground(Color.GRAY);
            numPanel.add(numButton);
        }

        MonopolyController rollCont = new RollController(model);
        MonopolyController helpCont = new HelpController(model);
        MonopolyController buyCont = new BuyController(model);
        MonopolyController passCont = new PassController(model);
        MonopolyController buildCont = new BuildController(model);
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
        buildButton = new JButton("View Available Tile To Build");
        buildButton.addActionListener(buildCont);
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
        BufferedImage img = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource("FinalMonopolyBoard.png");
            img = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaledImg = img.getScaledInstance(800,800,Image.SCALE_SMOOTH);
        ImageIcon boardImage = new ImageIcon(scaledImg);

        boardMap = new JLabel(boardImage);



        //Component Addition
        infoPane.add(activePlayerText);
        infoPane.add(walletStateText);
        infoPane.add(rollButton);
        infoPane.add(buyButton);
        infoPane.add(playerOverviewButton);
        infoPane.add(passButton);
        infoPane.add(buildButton);
        infoPane.add(helpButton);

        boardPane.add(boardMap, BorderLayout.CENTER);


        this.add(boardPane, BorderLayout.CENTER);
        this.add(infoPane, BorderLayout.EAST);

        //Packing
        numPlayerBotMenu.setDefaultCloseOperation(EXIT_ON_CLOSE);
        numPlayerBotMenu.pack();
        numPlayerMenu.setDefaultCloseOperation(EXIT_ON_CLOSE);
        numPlayerMenu.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        numPlayerMenu.setLocationRelativeTo(null);
        this.setLocationRelativeTo(numPlayerMenu);

        //Display
        numPlayerMenu.setVisible(true);
        numPlayerBotMenu.setVisible(false);
    }

    /**
     * Creates and returns the foreground color based on the brightness of the focus.
     * @param focus Color, the Color of center element of the screen.
     * @return Color,   white if the brightness of the focus is less than half,
     *
     */
    private Color makeForegroundColor(Color focus) {
        int red = focus.getRed();
        int green = focus.getGreen();
        int blue = focus.getBlue();
        double brightness = ((0.21 * (double) red) + (0.72 * (double) green) + (0.07 * (double) blue)) / 255.0;

        return (brightness > 0.5) ? Color.BLACK : Color.WHITE;
    }

    /**
     * Handle an information update received from the model.
     * @param e, a monopoly event
     */
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

        Color foregroundColor = makeForegroundColor(e.getActivePlayer().getCOLORCODE());

        infoPane.setBackground(e.getActivePlayer().getCOLORCODE());
        activePlayerText.setForeground(foregroundColor);
        walletStateText.setForeground(foregroundColor);

        JOptionPane.showMessageDialog(this,model.getEventString(),"Action Log",1);
    }

    public static void main(String[] args) {
        new MonopolyFrame();
    }

}
