import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class WatchingApp {

	static final int PORT = 2000;
	Socket socket;
	OutputStream os;
	InputStream is;
	BufferedWriter w;
	BufferedReader r;
	ObjectOutputStream objectOutputStream;
	Scanner sc;
	ArrayList<String> userCodes;
	String protectorCode;
	public WatchingApp() throws UnknownHostException, IOException {
		socket = new Socket("localhost",PORT);	
    	System.out.println("PORT("+PORT+")로 접속을 시도합니다.");
    	
    	os = socket.getOutputStream();
		is = socket.getInputStream();
		w = new BufferedWriter(new OutputStreamWriter(os));
		r = new BufferedReader(new InputStreamReader(is));
		objectOutputStream = new ObjectOutputStream(os);
		sc = new Scanner(System.in);
		userCodes = new ArrayList<String>();
		protectorCode="";
		
		
		checkProtectorCode();//여기서 protectorCode가없으면 생성하는작업 ㄱ
		
	}
	
	public void enrolProtector(String userCode) throws IOException{
		w.write(userCode+"\n");
		w.flush();
		addUserCodesToFile(userCode); //파일에 추가
		userCodes.add(userCode); //ArrayList에 추가
	}
	public void updateUserStatus() {
		
	}
	public void alert() {
		
	}
	public void write(String str) throws IOException {
		w.write(str+"\n");
		w.flush();
	}
	public void checkProtectorCode() throws IOException {
		String MyPath = System.getProperty("user.dir");
		File MyFile = new File(MyPath+"\\logWatcher.txt");
		FileReader filereader = new FileReader(MyFile);
		//BufferedReader bufReader = new BufferedReader(filereader);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(MyFile));
		if(!MyFile.exists()) {
			System.out.println("ProtectorCode가 없으므로 새로 만듭니다.");
			w.write("NewProtectorCode\n");
			w.flush();
			String name,pers_num,phone1,phone2;
			System.out.print("이름을 입력하세요: ");
			name = sc.nextLine();
			System.out.print("주민번호를 입력하세요: ");
			pers_num = sc.nextLine();
			System.out.print("phone1을 입력하세요: ");
			phone1 = sc.nextLine();
			System.out.print("phone2를 입력하세요: ");
			phone2 = sc.nextLine();
			w.write(name+"\n");
			w.flush();
			w.write(pers_num+"\n");
			w.flush();
			w.write(phone1+"\n");
			w.flush();
			w.write(phone2+"\n");
			w.flush();
			objectOutputStream.writeObject(this);
			protectorCode = r.readLine(); //protectorCode 받음
			bufferedWriter.write(protectorCode);
			bufferedWriter.close();
			filereader.close();
		}
	}
	
	public void updateUserCode() throws IOException {
		
		String MyPath = System.getProperty("user.dir");
		File MyFile = new File(MyPath+"\\logWatcher.txt");
		FileReader filereader = new FileReader(MyFile);
		BufferedReader bufReader = new BufferedReader(filereader);
		
		userCodes.clear();
		
		protectorCode = bufReader.readLine();
		String line = "";
		while((line = bufReader.readLine())!=null) {
			userCodes.add(line);
		}

		
		//BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(MyFile));
//		if(MyFile.isFile() && MyFile.canWrite()){   
//            bufferedwriter.write(protectorCode);
//            System.out.println("성공적으로 protectorCode를 만들었습니다.");
//		}else {
//			System.out.println("protectorCode쓰기 실패");
//		}
	}
	public void addUserCodesToFile(String Ucode) throws IOException {
		String MyPath = System.getProperty("user.dir");
		File MyFile = new File(MyPath+"\\logWatcher.txt");
		BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(MyFile,true));
		PrintWriter pw = new PrintWriter(bufferedwriter,true);
		pw.write(Ucode+"\n");
		pw.flush();
		pw.close();
	}
	
	public void show_userCodes() {
		int len = userCodes.size();
		System.out.println("당신이 추가한 UserCode들은 다음과 같습니다. 누구의 기록을 보고싶습니까?");
		for(int i=0;i<len;i++) {
			System.out.println(userCodes.get(i));
		}
		
	}
}
