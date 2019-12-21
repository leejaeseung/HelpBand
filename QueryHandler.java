import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryHandler {

	private int key = 0;
	private Connection conn = null;
	private java.sql.ResultSet curs;

	public QueryHandler() {
		connectDB();
		
		// test code
		try {
			this.insertUserInfo("진성호", "9999991111111", "male", 24);
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
		String query = "insert into userStatus value (?, ?, ?)";
		String newUsercode = generateCode();
		PreparedStatement stmt = conn.prepareStatement(query);

		stmt.setString(1, newUsercode);
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
}
