package AICodes;

import java.io.*;

public class GetRecipe {

    public static String fetchSuggestion(String ingredients) throws Exception {
        if (ingredients == null || ingredients.trim().isEmpty()) {
            return null;
        }

        // Build the command to call Ollama locally
        ProcessBuilder pb = new ProcessBuilder(
            "C:\\Users\\samso\\AppData\\Local\\Programs\\Ollama\\ollama.exe",
            "run",
            "llama3:1b", // or llama3:1b if needed
            "Suggest exactly ONE meal name using only these ingredients: " + ingredients + ". Do not add any description, just the meal name."

        );

        pb.redirectErrorStream(true); // merge stdout and stderr
        Process process = pb.start();

        // Read the output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }

        process.waitFor();
        return result.toString().trim();
    }
}
