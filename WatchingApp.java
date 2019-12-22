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
		
		w.write("watchApp\n");
		w.flush();
		
		System.out.println("1. User추가  2. 사용자 상태 확인 3. 이전 기록보기");
		String str = sc.nextLine();
		if(str.equals("1")) { //User 추가
			w.write("addUser\n");
			w.flush();
			String userCode;
			System.out.print("사용자의 UserCode를 입력하시오 : ");
			userCode = sc.nextLine();
			enrolProtector(userCode);
		}
		else if(str.equals("2")) { //사용자 상태 확인
			w.write("watch\n");
			w.flush();
		}
		else if(str.equals("3")) { //이전 기록 보기
			w.write("getLog\n");
			w.flush();
			int len = Integer.parseInt(r.readLine());
			System.out.println("당신이 추가한 UserCode들은 다음과 같습니다. 누구의 기록을 보고싶습니까?");
			for(int i=0;i<len;i++) {
				System.out.println(r.readLine());
			}
			if(len==0) {
				System.out.println("추가한 UserCode가 없습니다.");
			}
			else {
				String userCode = sc.nextLine();
				w.write(userCode+"\n");
				w.flush();
				
				for(int i=0;i<10;i++) {
					String status = r.readLine();
					String userStatus[] = status.split("/");
					for(int j=0;j<4;j++) {
						System.out.print(userStatus[j]);
					}
				}
				
			}
			
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
