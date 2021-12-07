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

    private JFrame internationalVersionMenu;
    private JPanel internationalVersionPanel;
    private JButton englishVersion;
    private JButton arabicVersion;
    private JLabel versionPanelText;

    private JButton rollButton;
    private JButton buyButton;
    private JButton passButton;
    private JButton playerOverviewButton;
    private JButton buildButton;
    private JButton helpButton;

    private JMenuBar slMenuBar; // save/load menu bar
    private JMenu saveLoadMenu;
    private JMenuItem saveMenuItem;
    private JMenuItem loadMenuItem;


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
        model.setupJailViews();

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

        //Number of Bots
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


        //International Versions
        internationalVersionMenu = new JFrame("Select the international version desired");
        internationalVersionMenu.setSize(new Dimension(400, 200));
        internationalVersionMenu.setLayout(new BorderLayout());
        internationalVersionPanel = new JPanel();
        internationalVersionPanel.setBackground(Color.WHITE);
        internationalVersionPanel.setPreferredSize(new Dimension(300,300));
        internationalVersionPanel.setLayout(new GridLayout(2,3));
        englishVersion = new JButton("English Version");
        arabicVersion = new JButton("Arabic Version");
        versionPanelText = new JLabel("What version would you like to play?", SwingConstants.CENTER);
        versionPanelText.setFont(stdFont);
        internationalVersionMenu.add(versionPanelText,BorderLayout.NORTH);

        internationalVersionPanel.add(englishVersion);
        internationalVersionPanel.add(arabicVersion);
        internationalVersionMenu.add(internationalVersionPanel,BorderLayout.CENTER);
        JFrame internationalFrame = internationalVersionMenu;


        englishVersion.addActionListener(e ->{
            internationalFrame.setVisible(false);
            numPlayerMenu.setVisible(true);
            model.importFormat("englishVersionXML.txt");

            //Adding Board Image
            BufferedImage img = null;
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                URL resource = classLoader.getResource("FinalMonopolyBoard.png"); //TO BE FIXED: WILL CHANGE WITH INTERNATIONAL VERSIONS
                assert resource != null;
                img = ImageIO.read(resource);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            assert img != null;
            Image scaledImg = img.getScaledInstance(800,800,Image.SCALE_SMOOTH);
            ImageIcon boardImage = new ImageIcon(scaledImg);
            boardMap = new JLabel(boardImage);
        });


        arabicVersion.addActionListener(e ->{
            internationalFrame.setVisible(false);
            numPlayerMenu.setVisible(true);
            model.importFormat("arabicVersionXML.txt");

            //Adding Board Image
            BufferedImage img = null;
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                URL resource = classLoader.getResource("FinalMonopolyBoard.png"); //TO BE FIXED: WILL CHANGE WITH INTERNATIONAL VERSIONS
                assert resource != null;
                img = ImageIO.read(resource);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            assert img != null;
            Image scaledImg = img.getScaledInstance(800,800,Image.SCALE_SMOOTH);
            ImageIcon boardImage = new ImageIcon(scaledImg);

            boardMap = new JLabel(boardImage);


        });

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

        //Create save/load menu
        slMenuBar = new JMenuBar();
        saveLoadMenu = new JMenu("Save/Load");
        saveMenuItem = new JMenuItem("Save Current Game");
        loadMenuItem = new JMenuItem("Load Previous Game");
        saveMenuItem.addActionListener(e -> { // save game

            try {
                model.exportMonopoly(JOptionPane.showInputDialog("Enter filename"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        loadMenuItem.addActionListener(e -> { //load game
            try {
                Monopoly temp = Monopoly.importMonopoly(JOptionPane.showInputDialog("Enter filename of saved game"));
                this.dispose()
                new MonopolyFrame(temp);
                temp.notifyViews();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        saveLoadMenu.add(saveMenuItem);
        saveLoadMenu.add(loadMenuItem);
        slMenuBar.add(saveLoadMenu);

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
            URL resource = classLoader.getResource("FinalMonopolyBoard.png"); //TO BE FIXED: WILL CHANGE WITH INTERNATIONAL VERSIONS
            assert resource != null;
            img = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
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
        this.add(slMenuBar, BorderLayout.NORTH);

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

        numPlayerMenu.setVisible(false);
        internationalFrame.setVisible(true);
        numPlayerBotMenu.setVisible(false);
    }

    /**
     * Initializes the GUI from a previously saved game, One Frame - Monopoly game
     */
    public MonopolyFrame(Monopoly model) {
        super("Group 6 Monopoly");
        this.setLayout(new BorderLayout());

        model.addView(this);
        model.setupJailViews();

        this.setSize(new Dimension(1200, 800));

        //Create Standard font
        Font stdFont = new Font("Comic Sans MS",Font.BOLD,20);


        MonopolyController rollCont = new RollController(model);
        MonopolyController helpCont = new HelpController(model);
        MonopolyController buyCont = new BuyController(model);
        MonopolyController passCont = new PassController(model);
        MonopolyController buildCont = new BuildController(model);
        MonopolyController stateCont = new PlayerStateController(model);

        //Create save/load menu
        slMenuBar = new JMenuBar();
        saveLoadMenu = new JMenu("Save/Load");
        saveMenuItem = new JMenuItem("Save Current Game");
        loadMenuItem = new JMenuItem("Load Previous Game");



        saveMenuItem.addActionListener(e -> { // save game
            try {
                model.exportMonopoly(JOptionPane.showInputDialog("Enter filename"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        loadMenuItem.addActionListener(e -> { //load game
            try {
                Monopoly temp = Monopoly.importMonopoly(JOptionPane.showInputDialog("Enter filename of saved game"));
                this.dispose();
                new MonopolyFrame(temp);
                temp.notifyViews();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        saveLoadMenu.add(saveMenuItem);
        saveLoadMenu.add(loadMenuItem);
        slMenuBar.add(saveLoadMenu);

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
            URL resource = classLoader.getResource("FinalMonopolyBoard.png"); //TO BE FIXED: WILL CHANGE WITH INTERNATIONAL VERSIONS
            assert resource != null;
            img = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
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
        this.add(slMenuBar, BorderLayout.NORTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        //Display

        this.setVisible(true);
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
            walletStateText.setText(model.versionFormat.getCurrencySign() + e.getActivePlayer().getWallet());
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
