package DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    public static boolean existsInDatabase(String mealName) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM recipes WHERE meal_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, mealName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public static void insertRecipe(String mealName, String ingredients, String instructions) {
        String query = "INSERT INTO recipes (meal_name, ingredients, instructions) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, mealName);
            stmt.setString(2, ingredients);
            stmt.setString(3, instructions);
            stmt.executeUpdate();

            System.out.println(" Recipe saved to database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
