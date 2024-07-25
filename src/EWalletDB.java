
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EWalletDB {
	private static final String DB_URL = "jdbc:derby:C:\\Users\\oscar\\MyDB;create=true"; // May need to change path for different machines
	private static final String USER = "user"; // Default User name
	private static final String PASS = "password"; // Default Password
	private static Connection conn = null;
	private static Statement stmt = null;

	public static void main(String[] args) {
		createConnection();
		testQueries();
		shutdown();
	}

	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection established");
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	public static boolean validateLogin(String username, String password) {
		String sql = "SELECT id FROM Users WHERE username = ? AND password = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void addExpense(int userId, String source, double amount, int yearlyfrequency) {
		String sql = "INSERT INTO Expense(user_id, source, amount, yearlyfrequency) VALUES(?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			pstmt.setString(2, source);
			pstmt.setDouble(3, amount);
			pstmt.setInt(4, yearlyfrequency);
			pstmt.executeUpdate();
			System.out.println("Expense added successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addIncome(int userId, String source, double amount, String month) {
		String sql = "INSERT INTO Income(user_id, source, amount, month) VALUES(?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			pstmt.setString(2, source);
			pstmt.setDouble(3, amount);
			pstmt.setString(4, month);
			pstmt.executeUpdate();
			System.out.println("Income added successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void testQueries() {
		// Sample queries for testing purposes
		System.out.println("Testing queries...");
		try {
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT * FROM Users");
			while (results.next()) {
				System.out.println("User ID: " + results.getInt("id") + ", Username: " + results.getString("username"));
			}

			results = stmt.executeQuery("SELECT * FROM Income");
			while (results.next()) {
				System.out.println("Income ID: " + results.getInt("id") + ", User ID: " + results.getInt("user_id")
						+ ", Source: " + results.getString("source") + ", Amount: " + results.getDouble("amount")
						+ ", Month: " + results.getString("month"));
			}

			results = stmt.executeQuery("SELECT * FROM Expense");
			while (results.next()) {
				System.out.println("Expense ID: " + results.getInt("id") + ", User ID: " + results.getInt("user_id")
						+ ", Source: " + results.getString("source") + ", Amount: " + results.getDouble("amount")
						+ ", Yearly Frequency: " + results.getInt("yearlyfrequency"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void shutdown() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				DriverManager.getConnection(DB_URL + ";shutdown=true");
				conn.close();
				System.out.println("Connection closed");
			}
		} catch (SQLException sqlExcept) {
			if (!sqlExcept.getSQLState().equals("XJ015")) {
				sqlExcept.printStackTrace();
			}
		}
	}

}

