package AICodes;
import DatabaseConnection.DatabaseManager;

public class MealProcessing {

    public static void handleMeal(String ingredients) {
        try {
            String mealName = GetRecipe.fetchSuggestion(ingredients);
            System.out.println("Suggested Meal Name: " + mealName);

            boolean exists = DatabaseManager.existsInDatabase(mealName);

            if (!exists) {
                RecipeApp.processMeal(mealName);
            } else {
                System.out.println("Meal already exists in the database. Skipping RecipeApp processing.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to process the meal.");
        }
    }
}
