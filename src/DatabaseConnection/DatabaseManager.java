package DatabaseConnection;

import java.sql.*;

public class DatabaseManager {

    public static boolean existsInDatabase(String mealName) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM recipes WHERE meal_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mealName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) exists = true;
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
            System.out.println("Recipe saved to database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getRecipeByName(String mealName) {
        String query = "SELECT ingredients, instructions FROM recipes WHERE meal_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mealName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String ingredients = rs.getString("ingredients");
                String instructions = rs.getString("instructions");
                StringBuilder sb = new StringBuilder();
                if (ingredients != null && !ingredients.trim().isEmpty()) {
                    sb.append("Ingredients:\n").append(ingredients).append("\n\n");
                }
                if (instructions != null && !instructions.trim().isEmpty()) {
                    sb.append("Instructions:\n").append(instructions);
                }
                return sb.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
