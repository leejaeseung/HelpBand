import java.sql.Connection;
import java.sql.DriverManager;

public class ServerDB {
	
	private Connection conn = null;
	
	public ServerDB() {
		connect();
	}
	
	private boolean connect() {
		String className = "org.gjt.mm.mysql.Driver";
		String url = "jdbc:mysql://localhost:3306/helpband?useSSL=false&useUnicode=true&characterEncoding=euckr";
		String user = "root";
		String password = "zfds1245";
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connection Successed!");
			
			return true;
		}
		catch(Exception e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			
			return false;
		}
	}
	
	private boolean disconnect() {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
			
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			
			return false;
		}
	}
}
