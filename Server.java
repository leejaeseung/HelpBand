import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
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
	Scanner sc;
	Socket socket;
	
	QueryHandler QH;
	
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
		
		QH = new QueryHandler();
		sc = new Scanner(System.in);
	}
	
	public void run() {
		String typ;
		try {
			typ = r.readLine();
			if(typ.equals("careApp")) {
				System.out.println("careApp 작동됨");
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
				System.out.println("watchApp 연결됨");
				
				String choice;
				choice = r.readLine();
				if(choice.equals("addUser")) { //user추가
					String userCode;
					userCode = r.readLine();
					//이 유저코드가 데이터베이스에 있는지 확인
					String[] userinfos = QH.getUserInfo(userCode);
					//case1: 데이터베이스 있을 때
					//보호자 등록시작
					if(userinfos[0]!=null) { //usercode가 있는 있는경우
						String protectorCode;
						protectorCode = getProtectorCode();
						if(protectorCode==null) {//프로텍터코드가 없을때
							protectorCode = addProtectorCode(); //프로텍터코드만듬
							QH.insertMatching(userCode,protectorCode); //맷칭~
						}
						
					}
					//case2: 데이터베이스에 없을때
					//없다고 알림(예외처리)
					else {
						System.out.println("잘못된 usercode입니다.");
					}
			
				}
				else if(choice.equals("watch")) {
					//데이터베이스에서 상태정보를 가져와 보여줌
					
				}
				else if(choice.equals("getLog")) {
					//데이터베이스에서 사용자의 최근 10개의 기록을 봄
					String protectorCode = getProtectorCode();
					if(protectorCode==null) {//프로텍터코드가 없을때
						System.out.println("protectorCode를 먼저 만들어주세요");
					}
					String[] users = QH.getMatchingByProtectorcode(protectorCode);
					int len = users.length;
					w.write(Integer.toString(len)+"\n"); 
					w.flush();
					//System.out.println("당신이 추가한 UserCode들은 다음과 같습니다. 누구의 기록을 보고싶습니까?");
					for(int i=0;i<len;i++) {
						System.out.println(users[i]);
					}
					if(len!=0) {
						String userCode = r.readLine();
						String userStatus[][] = new String[10][4];
						userStatus = QH.getUserStatus(userCode);
						for(int i=0;i<10;i++) {
							String status = "";
							status = userStatus[i][0]+"/"+userStatus[i][1]+"/"+userStatus[i][2]+"/"+userStatus[i][3];
							w.write(status+"\n"); 
							w.flush();
						}
					}
					
				}
			}
			else { // watchApp도아니고 careApp도 아니면 뭐냐
				System.out.println("@");
			}
			
		} catch (IOException | SQLException e) {
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
	String getProtectorCode() throws IOException {
		String protectorCode="";
		String MyPath = System.getProperty("user.dir");
		File MyFile = new File(MyPath+"\\logWatcher.txt");
		FileReader filereader = new FileReader(MyFile);
		BufferedReader bufReader = new BufferedReader(filereader);
		protectorCode = bufReader.readLine();
		System.out.println("getProtecorCode()에서 얻은 protectorCode : "+protectorCode);
		return protectorCode;
	}
	String addProtectorCode() throws SQLException, IOException {
		
		String name,pers_num,phone1,phone2;
		System.out.print("이름을 입력하세요: ");
		name = sc.nextLine();
		System.out.print("주민번호를 입력하세요: ");
		pers_num = sc.nextLine();
		System.out.print("phone1을 입력하세요: ");
		phone1 = sc.nextLine();
		System.out.print("phone2를 입력하세요: ");
		phone2 = sc.nextLine();
		
		String protectorCode = QH.insertProtector(name, pers_num, phone1, phone2);
		String MyPath = System.getProperty("user.dir");
		File MyFile = new File(MyPath+"\\logWatcher.txt");
		BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(MyFile));
		if(MyFile.isFile() && MyFile.canWrite()){   
            bufferedwriter.write(protectorCode);
            System.out.println("성공적으로 protectorCode를 만들었습니다.");
		}else {
			System.out.println("protectorCode쓰기 실패");
		}
		
		return protectorCode;
	}
	
}
