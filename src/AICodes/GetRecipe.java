package AICodes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRecipe {

    public static String fetchSuggestion(String ingredients) {
        if (ingredients == null || ingredients.trim().isEmpty()) {
            return "No ingredients provided.";
        }

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try {
            System.out.println("[DEBUG] Sending prompt to Ollama API...");

            URL url = new URL("http://127.0.0.1:11434/api/generate");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(60000);
            conn.setDoOutput(true);

            String prompt = "Create a short, realistic cooking recipe using only these ingredients: "
                    + ingredients
                    + ". Return the output in this exact format:\n"
                    + "Meal Name: <name>\nIngredients:\n- <ing1>\n- <ing2>\nSteps:\n1. <step1>\n2. <step2>\n\nReturn only this formatted text.";

            String json = "{\"model\":\"llama3:8b\",\"prompt\":\"" + escapeJson(prompt) + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
            }

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder responseBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.contains("\"response\":")) {
                    int start = line.indexOf("\"response\":\"") + 12;
                    int end = line.indexOf("\",\"done\"", start);
                    if (end == -1) end = line.lastIndexOf("\"}");
                    if (end > start) {
                        String chunk = line.substring(start, end)
                                .replace("\\n", "\n")
                                .replace("\\\"", "\"");
                        responseBuilder.append(chunk);
                    }
                }
            }

            String result = responseBuilder.toString().trim();
            if (result.isEmpty()) {
                return "Unable to generate recipe.";
            }

            String mealName = extractMealName(result);
            System.out.println("[DEBUG] Suggested Meal Name: " + mealName);

            return mealName; 

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching recipe";
        } finally {
            try { if (reader != null) reader.close(); } catch (Exception ignored) {}
            if (conn != null) conn.disconnect();
        }
    }

    private static String extractMealName(String text) {
        String mealName = "Unknown Meal";
        try {
            for (String line : text.split("\n")) {
                if (line.toLowerCase().startsWith("meal name:")) {
                    mealName = line.substring(line.indexOf(":") + 1).trim();
                    break;
                }
            }
        } catch (Exception ignored) {}


        if (mealName.length() > 250) {
            mealName = mealName.substring(0, 250);
        }
        return mealName;
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
    
    public static String fetchFullRecipe(String ingredients) {
        if (ingredients == null || ingredients.trim().isEmpty()) {
            return "No ingredients provided.";
        }

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try {

            URL url = new URL("http://127.0.0.1:11434/api/generate");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(60000);
            conn.setDoOutput(true);

            String prompt = "Create a short, realistic cooking recipe using only these ingredients: "
                    + ingredients
                    + ". Return in this exact readable format:\n"
                    + "Meal Name: <name>\n\nIngredients:\n- <ing1>\n- <ing2>\n\nSteps:\n1. <step1>\n2. <step2>\n\nReturn only this formatted text.";

            String json = "{\"model\":\"llama3:8b\",\"prompt\":\"" + escapeJson(prompt) + "\"}";

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();
            os.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            StringBuilder responseBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.contains("\"response\":")) {
                    int start = line.indexOf("\"response\":\"") + 12;
                    int end = line.indexOf("\",\"done\"", start);
                    if (end == -1) end = line.lastIndexOf("\"}");
                    if (end > start) {
                        String chunk = line.substring(start, end)
                                .replace("\\n", "\n")
                                .replace("\\\"", "\"");
                        responseBuilder.append(chunk);
                    }
                }
            }

            String fullRecipe = responseBuilder.toString().trim();
            if (fullRecipe.isEmpty()) {
                return "Unable to generate recipe.";
            }

            return fullRecipe;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching recipe";
        } finally {
            try { if (reader != null) reader.close(); } catch (Exception ignored) {}
            if (conn != null) conn.disconnect();
        }
    }

}
