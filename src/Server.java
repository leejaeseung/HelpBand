import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
	ObjectInputStream ob_reader;
	ObjectOutputStream ob_sender;

	Socket socket;
	
	static List<String> News = Collections.synchronizedList(new ArrayList<>());
		
	ConcurrentHashMap<String,Socket> Nets;
	Map<String, WatchingApp> ProtectorMap;
	
	Server(Socket socket,ConcurrentHashMap<String,Socket> Net) throws IOException{
		this.Nets = Net;
		ProtectorMap = new ConcurrentHashMap<>();
		
		this.socket = socket;
		is = socket.getInputStream();
		os = socket.getOutputStream();
		r = new BufferedReader(new InputStreamReader(is));
		w = new BufferedWriter(new OutputStreamWriter(os));
		dis = new DataInputStream(is);
		dos = new DataOutputStream(os);
		ob_reader = new ObjectInputStream(is);
		ob_sender = new ObjectOutputStream(os);
	}

	public void startEnroll() throws IOException, SQLException { //User의 등록
		String name,gender, pers_num, usercode;
		int age; //개인정보
		name = r.readLine();
		age = Integer.parseInt(r.readLine());
		pers_num = r.readLine();
		gender = r.readLine();
		usercode = QH.insertUserInfo(name, pers_num, gender, age);

		w.write(usercode + "\n");
		w.flush();
	}
	public void updateStatus() throws IOException, SQLException{
		ArrayList<WatchingApp> Watchers = new ArrayList<>();
		String usercode = r.readLine();

		String[] protectors = QH.getMatchingByUsercode(usercode);
		for (int i = 0; i < protectors.length ; i++) {
			Watchers.add(ProtectorMap.get(protectors[i]));
		}
		ob_sender.writeObject(Watchers);

		while(true){
			QH.insertUserStatus(usercode, Float.parseFloat(r.readLine()), Integer.parseInt(r.readLine()), r.readLine());
		}
	}
	public void startEnrollProtector() throws IOException, SQLException, ClassNotFoundException{
		String str[] = new String[4];
		for(int i=0;i<4;i++) {
			str[i] = r.readLine();
		}
		String protectorCode;
		protectorCode = QH.insertProtector(str[0], str[1], str[2], str[3]);
		ProtectorMap.put(protectorCode, (WatchingApp)ob_reader.readObject());
		w.write(protectorCode+"\n"); //protectorCode 보냄
		w.flush();
	}
	public void matchUser(String userCode, String protectorCode) throws IOException, SQLException{
		QH.insertMatching(userCode, protectorCode);
	}
	public void getLog(String userCode){

	}
}
