import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryHandler {

	private int key = 1;
	private Connection conn = null;
	private java.sql.ResultSet curs;

	public QueryHandler() {
		connectDB();
		
		// test code
		try {
//			this.insertUserInfo("진성호", "9999991111111", "male", 24);
//			this.getUserInfo("cfcd208495d565ef66e7dff9f98764da");
//			this.insertUserStatus("cfcd208495d565ef66e7dff9f98764da", 121, (float) 36.2);
//			insertProtector("홍길동", "8888881111111", "01033774979", "01033774979");
//			insertMatching("cfcd208495d565ef66e7dff9f98764da", "c4ca4238a0b923820dcc509a6f75849b");
			
//			String[] protectors = this.getMatchingByUsercode("cfcd208495d565ef66e7dff9f98764da");
//			for(int i=0; i<protectors.length; i++) {
//				System.out.println(protectors[i]);
//			}
//			System.out.println("======");
//			String[] users = this.getMatchingByProtectorcode("c4ca4238a0b923820dcc509a6f75849b");
//			for(int i=0; i<users.length; i++) {
//				System.out.println(users[i]);
//			}
			
			String[][] statuses = this.getUserStatus("cfcd208495d565ef66e7dff9f98764da");
			for(int i=0; i<statuses.length; i++) {
				for(int j=0; j<statuses[0].length; j++) {
					System.out.print(statuses[i][j] + " ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean connectDB() {
		String className = "org.gjt.mm.mysql.Driver";
		String url = "jdbc:mysql://localhost:3306/helpband?useSSL=false&useUnicode=true&characterEncoding=euckr";
		String user = "root";
		String password = "zfds1245";

		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);

			System.out.println("Connection Successed!");

			return true;
		} catch (Exception e) {
			System.out.println("Connection Failed!");

			e.printStackTrace();

			return false;
		}
	}

	public boolean disconnectDB() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public String generateCode() {
		String code = "";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(String.valueOf(key++).getBytes());

			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			code = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			code = null;
		}

		return code;
	}

	public void insertUserInfo(String name, String personalNumber, String gender, int age) throws SQLException {
		String query = "insert into userInfo value (?, ?, ?, ?, ?)";
		String newUsercode = generateCode();
		PreparedStatement stmt = conn.prepareStatement(query);

		stmt.setString(1, newUsercode);
		stmt.setString(2, name);
		stmt.setString(3, personalNumber);
		stmt.setString(4, gender);
		stmt.setInt(5, age);

		stmt.executeUpdate();
	}
	
	public void insertUserStatus(String usercode, int pulse, float temperature) throws SQLException {
		String query = "insert into userStatus (usercode, pulse, temperature) value (?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(query);

		stmt.setString(1, usercode);
		stmt.setInt(2, pulse);
		stmt.setFloat(3, temperature);

		stmt.executeUpdate();
	}
	
	public void insertProtector(String name, String personalNumber, String phone1, String phone2) throws SQLException {
		String query = "insert into protector value (?, ?, ?, ?, ?)";
		String newProtectorcode = generateCode();
		PreparedStatement stmt = conn.prepareStatement(query);

		stmt.setString(1, newProtectorcode);
		stmt.setString(2, name);
		stmt.setString(3, personalNumber);
		stmt.setString(4, phone1);
		stmt.setString(5, phone2);

		stmt.executeUpdate();
	}
	
	public void insertMatching(String usercode, String protectorcode) throws SQLException {
		String query = "insert into matching value (?, ?)";
		PreparedStatement stmt = conn.prepareStatement(query);

		stmt.setString(1, usercode);
		stmt.setString(2, protectorcode);

		stmt.executeUpdate();
	}
	
	public String[] getUserInfo(String usercode) throws SQLException {
		// 매개변수로 받은 usercode를 이용해 DB에서 해당하는 User의 정보들을 가져와
		// String 배열로 만들어서 반환
		
		String[] infos = new String[5];
		String query = "select * from userInfo where usercode = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, usercode);
		
		curs = stmt.executeQuery();
		curs.next();
		
		for(int i=1; i<=5; i++) {
			infos[i-1] = curs.getString(i);
		}
		
		return infos;
	}
	
	public String[][] getUserStatus(String usercode) throws SQLException {
		// 매개변수인 usercode를 이용해 해당 User의 최근 10번의
		// UserStatus를 DB에서 가져와 2차원 String 배열로 반환
		
		String[][] status = new String[10][4];
		String query = "select * from userstatus where usercode = ? order by 'date' desc";
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, usercode);
		
		curs = stmt.executeQuery();
		
		for(int i=0; i<10 && curs.next(); i++) {
			for(int j=2; j<=5; j++) {
				status[i][j-2] = curs.getString(j);
			}
		}
		
		return status;
	}
	
	public String[] getMatchingByUsercode(String usercode) throws SQLException {
		// usercode를 이용해 해당 User와 연결된 모든 보호자들의 protectorcode를 가져와
		// String 배열로 반환
		
		List<String> protectorcodes = new ArrayList<String>();
		String query = "select protectorcode from matching where usercode = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, usercode);
		
		curs = stmt.executeQuery();
		
		while(curs.next()) {
			protectorcodes.add(curs.getString(1));
		}
		
		return protectorcodes.toArray(new String[protectorcodes.size()]);
	}
	
	public String[] getMatchingByProtectorcode(String protectorcode) throws SQLException {
		// protectorcode를 이용해 해당 보호자와 연결된 모든 User들의 usercode를 가져와
		// String 배열로 반환
		
		List<String> usercodes = new ArrayList<String>();
		String query = "select usercode from matching where protectorcode = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, protectorcode);
		
		curs = stmt.executeQuery();
		
		while(curs.next()) {
			usercodes.add(curs.getString(1));
		}
		
		return usercodes.toArray(new String[usercodes.size()]);
	}
}
