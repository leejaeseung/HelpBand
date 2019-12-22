import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class CareApp {

	static final int PORT = 2000;
	Socket socket;
	OutputStream os;
	InputStream is;
	BufferedWriter w;
	BufferedReader r;

	private String usercode;
    private UserInfo UI;
    private Device myDevice;
    private Bands myBand;

    public void StartBioAnalyze() throws InterruptedException{
    Scanner sc;

//    public CareApp(String name, int age, int personalNum, Bands myBand){
//        UI = new UserInfo(name, age, personalNum);
//        this.myBand = myBand;
//        this.myDevice = new Adapter();
//    }

    public CareApp(Bands MyBand) throws IOException, InterruptedException {

    	this.myDevice = new Adapter();
    	myBand = MyBand;

    	socket = new Socket("localhost",PORT);
    	System.out.println("PORT("+PORT+")로 접속을 시도합니다.");

    	os = socket.getOutputStream();
		is = socket.getInputStream();
		w = new BufferedWriter(new OutputStreamWriter(os));
		r = new BufferedReader(new InputStreamReader(is));
		sc = new Scanner(System.in);


		System.out.println("1.사용자 등록하기  2.careApp 작동하기");
		int choice = sc.nextInt();
		sc.nextLine();
		if(choice==1) {
			w.write("careApp"+"\n");
			w.flush();
			w.write("1\n"); //사용자 등록
    		w.flush();
			enrolUser();
			w.write(UI.getName()+"\n");
    		w.flush();
    		w.write(Integer.toString(UI.getAge())+"\n");
    		w.flush();
    		w.write(Integer.toString(UI.getPersonalNum())+"\n");
    		w.flush();
    		w.write(UI.getGender()+"\n");
    		w.flush();

    		//이렇게 하고 나중에 DB에서 UserCode 주면 파일로 만들어서 userCode 저장?
		}
		else if(choice==2) {
			w.write("careApp"+"\n");
			w.flush();
    		w.write("2\n"); //careApp작동
    		w.flush();

			StartBioAnalyze();
		}
		else {
			System.out.println("잘못된 명령입니다.");
		}
    }

    public void StartBioAnalyze() throws InterruptedException, IOException{
        JudgeAI_IF JA = new JudgeAI();
        String str;
        JA.match(UI.getProtectorList());
        while(true) {
            str = JA.diagnose(myDevice.measure(myBand));
            System.out.println(str);
            w.write(str+"\n");
    		w.flush();

            Thread.sleep(1000);
        }
    }
    public void enrolUser(String name, int age, int personalNum, Bands myBand){
        UI = new UserInfo(name, age, personalNum);
        this.myBand = myBand;
        this.myDevice = new Adapter();
    public void enrolUser(){
    	String name,gender;
    	int age, pers_num;
    	System.out.print("이름을 입력하시오 : ");
    	name = sc.nextLine();
    	System.out.print("주민번호를 입력하시오 : ");
    	pers_num = sc.nextInt();
    	sc.nextLine();
    	System.out.print("성별을 입력하시오 : ");
    	gender = sc.nextLine();
    	System.out.print("나이를 입력하시오 : ");
    	age = sc.nextInt();
    	UI = new UserInfo(name,pers_num,gender,age);
    }
}
