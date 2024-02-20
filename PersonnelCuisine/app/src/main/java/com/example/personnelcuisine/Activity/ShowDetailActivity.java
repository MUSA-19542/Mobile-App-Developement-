package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.personnelcuisine.R;

import java.util.List;

import Domain.FoodsDomain;
import Helper.CDbHelper;
import Helper.ManagementCart;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn, titletxt, feetxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, MinusBtn, picFood;
    private FoodDataModel object;
    int numberOrder = 1;
   private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }

    public void getBundle() {
        long foodId = getIntent().getLongExtra("food_id", -1);
        if (foodId != -1) {
            // Fetch additional information from the database based on the ID
            object = getFoodDetailsFromDatabase(foodId); // Assign the retrieved FoodDataModel to object
            if (object != null) {
                byte[] imageByteArray = object.getImage();
                Glide.with(this)
                        .load(imageByteArray)
                        .into(picFood);

                titletxt.setText(object.getName());
                feetxt.setText(String.valueOf(object.getPrice()));
                descriptionTxt.setText(object.getDescription());
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        }

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder++;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        MinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder > 1) {
                    numberOrder--;
                } else {
                    Toast.makeText(ShowDetailActivity.this, "Don't Test Me, I will not go less than 1, You Fool!", Toast.LENGTH_LONG).show();
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                object.setNumberInCart(numberOrder);
                 managementCart.insertFood(object);
            }
        });
    }
    private FoodDataModel getFoodDetailsFromDatabase(long foodId) {
        // Retrieve additional information from the database based on the ID
        CDbHelper dbHelper = new CDbHelper(this);
        List<FoodDataModel> foods = dbHelper.getAllFood();
        for (FoodDataModel food : foods) {
            if (food.getId() == foodId) {
                return food;
            }
        }
        return null;
    }
    public void initView()
    {
        addToCartBtn=findViewById(R.id.AddToCartBtn);
        titletxt=findViewById(R.id.titleTxt);
        feetxt=findViewById(R.id.priceTxt);
        descriptionTxt=findViewById(R.id.descriptionTxt);
        numberOrderTxt=findViewById(R.id.numberOrderTxt);
        plusBtn=findViewById(R.id.plusBtn);
        MinusBtn=findViewById(R.id.minusBtn);
        picFood=findViewById(R.id.picfood);



    }
}