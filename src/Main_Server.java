import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Main_Server {

	static final int PORT = 2000;
	static ConcurrentHashMap<String,Socket> Nets;

	public static void main(String[] args) {
		
		Nets = new ConcurrentHashMap<>();
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			
			System.out.println("서버 : 클라이언트 접속을 대기합니다.");
			while(true) { //run forever..
				Socket socket = serverSocket.accept();
				Thread t = new Server(socket,Nets);
				System.out.println("<Server>");
				System.out.println("서버 "+socket.getInetAddress()+
						" 클라이언트와 "+socket.getLocalPort()+"포트로 연결되었습니다.");
				t.start();
				
				
				
			}
		}catch(IOException e) {
			System.out.println("serverSocket fail().."+e.getMessage());
		}
		finally{
			System.out.println("server 종");
		}
	}

}
