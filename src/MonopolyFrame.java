import javax.swing.*;
import java.awt.*;

public class MonopolyFrame extends JFrame implements MonopolyView{

    private JButton roll;
    private JButton buy;
    private JButton pass;
    private JButton viewProp;

    private JLabel wallet;
    private JLabel player;

    private JPanel boardPane;
    private JPanel butPane;

    public MonopolyFrame(){
        super("Monopoly6 [TEMP NAME]");

        this.setLayout(new BorderLayout());
        Monopoly model = new Monopoly();
        model.addView(this);

        this.setSize(new Dimension(1200 ,800));

        boardPane.setSize(800,800);
        butPane.setSize(400,800);

        this.add(boardPane);
        this.add(butPane);


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        MonopolyController mc = new MonopolyController(model);




        this.setVisible(true);
    }

    @Override
    HandleMonopolyUpdate(MonopolyEvent e){
        //Dependent On Undefined Class
    }

    public static void main(String[] args) {
        new MonopolyFrame();
    }

}
