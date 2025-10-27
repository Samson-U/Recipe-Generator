package AICodes;

public class RecipeApp {

    // Process a meal name
    public static void processMeal(String mealName) throws Exception {
        System.out.println("Processing Meal: " + mealName);

        // Fetch the full recipe
        String rawRecipe = RecipeFetcher.getRecipe(mealName);
        System.out.println("\nOriginal Recipe:\n" + rawRecipe);

        // Refine the recipe using AIRefiner
        String refinedRecipe = AIRefiner.refineRecipe(rawRecipe);
        System.out.println("\nAI-Refined Recipe:\n" + refinedRecipe);
    }
}
