package dal.admins;

import db.Database;

import java.sql.*;

public class AdminDAO {
    public AdminDAO() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS admins (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL)";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfAdminExists(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement ptstmt = conn.prepareStatement(sql)
        ) {
            ptstmt.setString(1, username);
            ptstmt.setString(2, password);

            try (ResultSet rs = ptstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAdmin(String username, String password) {
        String checkQuery = "SELECT COUNT(*) FROM admins WHERE username = ?";
        String insertQuery = "INSERT INTO admins (username, password) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Username already exists
            }

            insertStmt.setString(1, username);
            insertStmt.setString(2, password); // Consider hashing the password
            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
