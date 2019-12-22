import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
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

    static final int PORT = 2000;
    Socket socket;
    OutputStream os;
    InputStream is;
    BufferedWriter w;
    BufferedReader r;

    Scanner sc;

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


	public WatchingApp() throws UnknownHostException, IOException {
		socket = new Socket("localhost",PORT);
    	System.out.println("PORT("+PORT+")로 접속을 시도합니다.");

    	os = socket.getOutputStream();
		is = socket.getInputStream();
		w = new BufferedWriter(new OutputStreamWriter(os));
		r = new BufferedReader(new InputStreamReader(is));
		sc = new Scanner(System.in);

		System.out.println("1. 보호자 등록  2. 사용자 상태 확인");
		String str = sc.nextLine();
		if(str.equals("1")) { //보호자 등록
			w.write("Enrol\n");
			w.flush();
			String userCode;
			System.out.print("사용자의 UserCode를 입력하시오 : ");
			userCode = sc.nextLine();
			enrolProtector(userCode);
		}
		else if(str.equals("2")) { //사용자 상태 확인
			w.write("Watch\n");
			w.flush();
		}
		else {
			System.out.println("잘못된 입력입니다.");
		}

	}
	public void enrolProtector(String userCode) throws IOException{
		w.write(userCode+"\n");
		w.flush();
	}
}
