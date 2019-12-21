import javax.swing.*;
import java.awt.*;

public class WatchingApp implements Observer{

    private int usercode;
    private UserStatus myUS;
    private JFrame JF;
    private JLabel TemperLabel;
    private JLabel PulseLabel;
    private JLabel SymptomLabel;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public WatchingApp(int num){

        JF = new JFrame();
        JF.setTitle("보호자" + num);
        JF.setSize(250, 250);
        //JF.setLocationRelativeTo(null);
        JF.setLocation(250*(num - 1), (dim.height/2)-(JF.getHeight()/2));
        JF.setResizable(false);
        JF.setLayout(null);
        JF.setVisible(true);
        JF.setDefaultCloseOperation(JF.EXIT_ON_CLOSE);

        TemperLabel = new JLabel();
        TemperLabel.setOpaque(true);
        TemperLabel.setBackground(new Color(0,0,0,0));
        TemperLabel.setBounds(35,55,300,50);
        TemperLabel.setForeground(Color.black);

        PulseLabel = new JLabel();
        PulseLabel.setOpaque(true);
        PulseLabel.setBackground(new Color(0,0,0,0));
        PulseLabel.setBounds(35,75,300,50);
        PulseLabel.setForeground(Color.black);

        SymptomLabel = new JLabel();
        SymptomLabel.setOpaque(true);
        SymptomLabel.setBackground(new Color(0,0,0,0));
        SymptomLabel.setBounds(35,95,300,50);
        SymptomLabel.setForeground(Color.black);

        JF.add(TemperLabel);
        JF.add(PulseLabel);
        JF.add(SymptomLabel);
    }
    public void enrolProtector(){

    }
    public void display(){

        TemperLabel.setText("체온 : " + myUS.getTemperature());
        PulseLabel.setText("맥박 : " + myUS.getPulse());
        SymptomLabel.setText("증상 : " + myUS.getSymptom());
        JF.repaint();

        //System.out.println("보호자 " + mynum);
        //System.out.println("체온 : " + myUS.getTemperature());
        //System.out.println("맥박 : " + myUS.getPulse());
        //System.out.println("증상 : " + myUS.getSymptom());
    }
    public void updateUserStatus(UserStatus US){
        myUS = US;
        display();
    }
    public void alert(){
        JFrame AlertFrame = new JFrame();
        AlertFrame.setTitle("위급 상황");
        AlertFrame.setSize(250, 250);
        //AlertFrame.setLocationRelativeTo();
        AlertFrame.setResizable(false);
        AlertFrame.setLayout(null);
        AlertFrame.setVisible(true);
        //System.out.println("비상!비상!비상!!!!!!!!");
    }
}
