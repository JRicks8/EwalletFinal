
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EWalletDB {
    private static String DB_URL = "jdbc:derby:C:\\Users\\oscar\\MyDB;create=true"; // Adjust the path as necessary
    private static String USER = "user"; // Default User name
    private static String PASS = "password"; // Default Password
    private static Connection conn = null;
    private static Statement stmt = null;
    
    static {
    	createConnection();
    }


    public static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection established");
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public static boolean validateLogin(String username, String password) {
        String sql = "SELECT id FROM APP.Users WHERE username = ? AND password = ?";
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
        String sql = "INSERT INTO APP.Expenses(user_id, source, amount, yearlyfrequency) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, source);
            pstmt.setDouble(3, amount);
            pstmt.setInt(4, yearlyfrequency);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Expense added successfully, rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addIncome(int userId, String source, double amount, String month) {
        String sql = "INSERT INTO APP.Income(user_id, source, amount, month) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, source);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, month);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Income added successfully, rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean tryLoginAsUser(String inputUsername, String inputPassword) {
		String sql = "SELECT username,password FROM APP.Users WHERE username=" + inputUsername;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			ResultSet rs = pstmt.executeQuery();
			if (rs.first()) {
				
				if (rs.getString(2).equals(inputPassword)) {
					return true;
				}
				return false;
			}
			else {
				
				return true;
				
			}
			
			
		} catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
    
    
    public static void shutdown() {
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

