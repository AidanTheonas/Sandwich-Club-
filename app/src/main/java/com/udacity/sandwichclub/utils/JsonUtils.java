package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        JSONObject jsonNameDetails;
        JSONObject jsonGeneralDetails;
        try {
            sandwich = new Sandwich();
            jsonGeneralDetails = new JSONObject(json);
            jsonNameDetails = jsonGeneralDetails.getJSONObject("name");

            String mainName = jsonNameDetails.optString("mainName");
            sandwich.setMainName(mainName);

            String placeOfOrigin = jsonGeneralDetails.optString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            String description = jsonGeneralDetails.optString("description");
            sandwich.setDescription(description);

            String image = jsonGeneralDetails.getString("image");
            sandwich.setImage(image);

            JSONArray jsonAlsoKnowsAsArray = new JSONArray(jsonNameDetails.optString("alsoKnownAs"));
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < jsonAlsoKnowsAsArray.length(); i++) {
                alsoKnownAs.add(jsonAlsoKnowsAsArray.get(i).toString());
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);
            JSONArray jsonIngredientsArray = new JSONArray(jsonGeneralDetails.optString("ingredients"));
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < jsonIngredientsArray.length(); i++) {
                ingredients.add(jsonIngredientsArray.get(i).toString());
            }
            sandwich.setIngredients(ingredients);

        } catch (Exception e) {
            Log.e("Error:", e.toString());
        }
        return sandwich;
    }
}
