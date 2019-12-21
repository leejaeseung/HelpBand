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

public class WatchingApp {

	static final int PORT = 2000;
	Socket socket;
	OutputStream os;
	InputStream is;
	BufferedWriter w;
	BufferedReader r;
	
	Scanner sc;
	
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
	public void updateUserStatus() {
		
	}
	public void alert() {
		
	}
}
