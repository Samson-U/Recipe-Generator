package AICodes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecipeFetcher {
    public static String getRecipe(String mealName) throws Exception {
        String apiUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + mealName.replace(" ", "%20");
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        JSONObject json = new JSONObject(sb.toString());
        JSONArray meals = json.getJSONArray("meals");
        if (meals == null || meals.length() == 0) return "Recipe not found";

        JSONObject meal = meals.getJSONObject(0);

        String instructions = meal.getString("strInstructions");

        StringBuilder ingredients = new StringBuilder();
        for (int i = 1; i <= 20; i++) {
            String ingredient = meal.optString("strIngredient" + i);
            String measure = meal.optString("strMeasure" + i);
            if (ingredient != null && !ingredient.isEmpty() && !ingredient.equals("null")) {
                ingredients.append(measure).append(" ").append(ingredient).append("\n");
            }
        }

        return "Ingredients:\n" + ingredients.toString() + "\nInstructions:\n" + instructions;
    }
}
