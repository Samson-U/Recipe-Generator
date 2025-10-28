package AICodes;

public class AIRefiner {

    public static String refineRecipe(String rawText) {
        if (rawText == null || rawText.trim().isEmpty())
            return "No recipe generated.";

        String refined = rawText
                .replaceAll("(?i)Here is a recipe:?\\s*", "")
                .replaceAll("\\\\n", "\n")
                .replaceAll("\\s{3,}", "\n");

        return refined.trim();
    }
}
