package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/recipe_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "samson";

    // Compatible with older Java: handle exceptions inside
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver"); // Load driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if connection fails
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connected to PostgreSQL successfully!");
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
