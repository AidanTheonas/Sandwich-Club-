package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String NAME = "name";
    private static final String MAIN_MAME = "main_name";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        JSONObject jsonNameDetails;
        JSONObject jsonGeneralDetails;
        try {
            sandwich = new Sandwich();
            jsonGeneralDetails = new JSONObject(json);

            if (jsonGeneralDetails.has(NAME)) {
                jsonNameDetails = jsonGeneralDetails.getJSONObject(NAME);
                if (jsonNameDetails.has(MAIN_MAME)) {
                    String mainName = jsonNameDetails.optString(MAIN_MAME);
                    sandwich.setMainName(mainName);
                }
                if (jsonGeneralDetails.has(ALSO_KNOWN_AS)) {
                    JSONArray jsonAlsoKnowsAsArray = new JSONArray(jsonNameDetails.optString(ALSO_KNOWN_AS));
                    List<String> alsoKnownAs = new ArrayList<>();
                    for (int i = 0; i < jsonAlsoKnowsAsArray.length(); i++) {
                        alsoKnownAs.add(jsonAlsoKnowsAsArray.get(i).toString());
                    }
                    sandwich.setAlsoKnownAs(alsoKnownAs);
                }
            }

            if (jsonGeneralDetails.has(PLACE_OF_ORIGIN)) {
                String placeOfOrigin = jsonGeneralDetails.optString(PLACE_OF_ORIGIN);
                sandwich.setPlaceOfOrigin(placeOfOrigin);
            }

            if (jsonGeneralDetails.has(DESCRIPTION)) {
                String description = jsonGeneralDetails.optString(DESCRIPTION);
                sandwich.setDescription(description);
            }

            if (jsonGeneralDetails.has(IMAGE)) {
                String image = jsonGeneralDetails.getString(IMAGE);
                sandwich.setImage(image);
            }

            if (jsonGeneralDetails.has(INGREDIENTS)) {
                JSONArray jsonIngredientsArray = new JSONArray(jsonGeneralDetails.optString(INGREDIENTS));
                List<String> ingredients = new ArrayList<>();
                for (int i = 0; i < jsonIngredientsArray.length(); i++) {
                    ingredients.add(jsonIngredientsArray.get(i).toString());
                }
                sandwich.setIngredients(ingredients);
            }

        } catch (Exception e) {
            Log.e("Error:", e.toString());
        }
        return sandwich;
    }
}
