package com.example.foodlist;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ActivityDetail extends AppCompatActivity {
  TextView TxtNamed,TxtPriced,TxtLocationed,TxtDescriptiond;
  ImageView Btncall,Btnmessage,PicUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        long itemId = getIntent().getLongExtra("item_Id", -1);

        FoodModel food = getFoodData(itemId);

        // Populate the UI elements with the retrieved data
        if (food != null) {
            TxtNamed.setText(food.getName());
            TxtDescriptiond.setText(food.getDescription());
            TxtLocationed.setText(food.getLocation());
            TxtPriced.setText("$  "+(food.getPrice()));

            // Load the image using Glide or any other image loading library
            Glide.with(this).load(Uri.parse(food.getImageUri())).into(PicUser);


        }


        Btnmessage.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                String message = food.getNumber();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + message));
                startActivity(intent);
            }
        });

        Btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = food.getNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
    }


    public void initView()
    {
        TxtNamed=findViewById(R.id.txtName);
        TxtPriced=findViewById(R.id.txtPrice);
        TxtLocationed=findViewById(R.id.txtLocation);
        TxtDescriptiond=findViewById(R.id.txtDescription);
        Btncall=findViewById(R.id.callBtn);
        Btnmessage=findViewById(R.id.messageBtn);
        PicUser=findViewById(R.id.pic_add);

    }
    private FoodModel getFoodData(long itemId) {

        DbHelper dbHelper = new DbHelper(this);

        return dbHelper.getFoodById(itemId);
    }

}