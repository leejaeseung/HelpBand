import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread implements ServerIF{

	InputStream is;
	OutputStream os;
	BufferedReader r;
	BufferedWriter w;
	FileOutputStream fos;
	FileInputStream fis;
	DataInputStream dis;
	DataOutputStream dos;
	
	Socket socket;
	
	static List<String> News = Collections.synchronizedList(new ArrayList<>());
		
	ConcurrentHashMap<String,Socket> Nets;
	
	Server(Socket socket,ConcurrentHashMap<String,Socket> Net) throws IOException{
		this.Nets = Net;
		
		this.socket = socket;
		is = socket.getInputStream();
		os = socket.getOutputStream();
		r = new BufferedReader(new InputStreamReader(is));
		w = new BufferedWriter(new OutputStreamWriter(os));
		dis = new DataInputStream(is);
		dos = new DataOutputStream(os);
	}
	
	public void run() {
		String typ;
		try {
			typ = r.readLine();
			if(typ.equals("careApp")) {
				String choice;
				choice = r.readLine();
				if(choice.equals("1")) { //careApp등록
					startEnroll();
					
				}
				else if(choice.equals("2")){ //careApp진단 작동 
					String msg;
					while(true) {	
						msg = r.readLine();
						System.out.println("사용자 상태 : "+msg);
					
					
						
					}	
				}
			}			
			else if(typ.equals("watchApp")) {
				String choice;
				choice = r.readLine();
				if(choice.equals("Enrol")) {
					String userCode;
					userCode = r.readLine();
					//이 유저코드가 데이터베이스에 있는지 확인
					//case1: 데이터베이스에 없을때
					//없다고 알림(예외처리)
					
					//case2: 데이터베이스 있을 때
					//보호자 등록시작
				}
				else if(choice.equals("Watch")) {
					//데이터베이스에서 상태정보를 가져와 보여줌
				}
			}
			else { // watchApp도아니고 careApp도 아니면 뭐냐
				System.out.println("@");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	void startEnroll() throws IOException { //User의 등록
		String name,gender;
		int age, pers_num; //개인정보
		name = r.readLine();
		age = Integer.parseInt(r.readLine());
		pers_num = Integer.parseInt(r.readLine());
		gender = r.readLine();
		System.out.println("사용자의 이름: "+name+", 나이: "+age+", 주민번호: "+pers_num+", 성별: "+gender);
		
		String UserInfo = "UserInfo/"+name+"/"+pers_num+"/"+gender+"/"+age;
		//usercode 생성,
		//usercode 전달
		//w.write(usercode+"\n");
		//w.flush();
	}
	
}
