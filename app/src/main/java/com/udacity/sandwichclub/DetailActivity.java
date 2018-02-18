package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        } else {
            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }
            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView tvAlsoKnown = findViewById(R.id.also_known_tv);
        List<String> lAlsoKnownAs = sandwich.getAlsoKnownAs();
        String strAlsoKnownAs = listToString(lAlsoKnownAs);
        if (strAlsoKnownAs.trim().equals("")) {
            tvAlsoKnown.setText(getResources().getString(R.string.none));
        } else {
            tvAlsoKnown.setText(strAlsoKnownAs);
        }

        TextView tvOrgin = findViewById(R.id.origin_tv);
        String strPlaceOfOrigin = sandwich.getPlaceOfOrigin();
        if (strAlsoKnownAs.trim().equals("")) {
            tvOrgin.setText(getResources().getString(R.string.unkonown));
        } else {
            tvOrgin.setText(strPlaceOfOrigin);
        }

        List<String> lIngredients = sandwich.getIngredients();
        String strIngredients = listToString(lIngredients);
        TextView tvIngredients = findViewById(R.id.ingredients_tv);
        if (strIngredients.trim().equals("")) {
            tvIngredients.setText(getResources().getString(R.string.unkonown));
        } else {
            tvIngredients.setText(strIngredients);
        }

        String strDescription = sandwich.getDescription();
        TextView tvDescription = findViewById(R.id.description_tv);
        if (strDescription.trim().equals("")) {
            tvDescription.setText(getResources().getString(R.string.none));
        } else {
            tvDescription.setText(strDescription);
        }
    }

    public String listToString(List<String> list) {
        return TextUtils.join(", ", list);
    }
}
