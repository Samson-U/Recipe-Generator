package AICodes;

import java.io.*;

public class AIRefiner {
    public static String refineRecipe(String recipeText) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
            "C:\\Users\\samso\\AppData\\Local\\Programs\\Ollama\\ollama.exe",
            "run",
            "gemma3:1b",
            "Make this recipe simpler and list the steps clearly:\n" + recipeText
        );

        pb.redirectErrorStream(true); 
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }

        process.waitFor();
        return result.toString();
    }
}
